#   Copyright (c) 2019-2020 AnimatedLEDStrip
#
#   Permission is hereby granted, free of charge, to any person obtaining a copy
#   of this software and associated documentation files (the "Software"), to deal
#   in the Software without restriction, including without limitation the rights
#   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
#   copies of the Software, and to permit persons to whom the Software is
#   furnished to do so, subject to the following conditions:
#
#   The above copyright notice and this permission notice shall be included in
#   all copies or substantial portions of the Software.
#
#   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
#   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
#   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
#   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
#   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
#   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
#   THE SOFTWARE.

import socket
from threading import Thread
from typing import Optional, Dict, AnyStr

from .animation import Animation
from .animation_data import AnimationData


class AnimationSender(object):
    """Handles communications with the server"""

    def __init__(self, ip_address: str, port_num: int):
        self.address: str = ip_address
        self.port: int = port_num
        self.connection: 'socket.socket' = socket.socket()
        self.connected: bool = False
        self.recv_thread: Optional['Thread'] = None
        self.running_animations: Dict[str, 'AnimationData'] = {}

    def start(self):
        """Connect to the server"""
        # Attempt to connect to the server
        self.connection = socket.create_connection((self.address, self.port), timeout=2.0)

        # Connection has been made, so set connected = True
        self.connected = True

        # Create and start a separate thread for receiving animations
        self.recv_thread = Thread(target=self.recv_animations, daemon=True)
        self.recv_thread.start()

    def end(self):
        """Disconnect from the server"""
        # Disconnect from the server
        self.connection.close()

        # Connection has been closed, so set connected = False
        self.connected = False

        # If the separate thread for receiving animations was started, join it with the main thread.
        # The loop should stop because the connection is closed and connected is False,
        #  allowing it to return
        if self.recv_thread is not None:
            self.recv_thread.join()

    def send_animation(self, animation_json: AnyStr):
        """Send a new animation to the server"""
        json_bytes = bytearray(animation_json, 'utf-8')
        self.connection.sendall(json_bytes)

    def recv_animations(self):
        """Loop that runs in a separate thread to receive new and ending animations from the server"""
        while self.connected:
            try:
                input_bytes = self.connection.recv(4096)

                # Split up animations (multiple may have come in the same message -
                #  they are split up with semicolons)
                for input_str in input_bytes.split(bytes(';', 'utf-8')):
                    # Make sure we're dealing with an animation
                    # (other messages can start with INFO: or CMD :, for example)
                    if input_str.startswith(bytes('DATA:', 'utf-8')):

                        # Create the AnimationData instance
                        data = AnimationData.from_json(input_str)

                        # Handle new and ending animations separately
                        if data.animation != Animation.ENDANIMATION:
                            # Animation is a new animation, so add it to the running_animations dict
                            self.running_animations[data.id] = data
                        elif data.id in self.running_animations:
                            # Animation is an ending animation, so remove it if it's in the dict
                            del self.running_animations[data.id]

            except socket.timeout:
                pass
            except OSError:
                pass
