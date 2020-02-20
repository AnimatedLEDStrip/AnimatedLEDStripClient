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

from .Animation import Animation
from .AnimationData import AnimationData


class AnimationSender(object):

    def __init__(self, ip_address, port_num):
        self.address = ip_address
        self.port = port_num
        self.connection = socket.socket()
        self.connected = False
        self.recv_thread = None
        self.running_animations = {}

    def start(self):
        self.connection = socket.create_connection((self.address, self.port), timeout=2.0)
        self.connected = True
        self.recv_thread = Thread(target=self.recv_animations, daemon=True)
        self.recv_thread.start()

    def end(self):
        self.connection.close()
        self.connected = False
        if self.recv_thread is not None:
            self.recv_thread.join()

    def send_animation(self, animation_json):
        json_bytes = bytearray(animation_json, 'utf-8')
        self.connection.sendall(json_bytes)

    def recv_animations(self):
        while self.connected:
            try:
                input_bytes = self.connection.recv(4096)
                for input_str in input_bytes.split(bytes(';', 'utf-8')):
                    if input_str.startswith(bytes('DATA:', 'utf-8')):
                        data = AnimationData()
                        data.from_json(input_str)
                        if data.animation != Animation.ENDANIMATION:
                            self.running_animations[data.id] = data
                        elif data.id in self.running_animations:
                            del self.running_animations[data.id]
            except socket.timeout:
                pass
            except OSError:
                pass
