import socket


class AnimationSender(object):

    def __init__(self, ip_address, port_num):
        self.address = ip_address
        self.port = port_num
        self.connection = socket.socket()

    def start(self):
        self.connection = socket.create_connection((self.address, self.port))

    def end(self):
        self.connection.close()

    def send_animation(self, animation_json):
        json = bytearray(animation_json, "utf-8")
        self.connection.send(json)
