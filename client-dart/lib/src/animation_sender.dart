import 'dart:io';
import 'package:animatedledstripclient/src/animation_data.dart';

class AnimationSender {
  String address;
  int port;
  Socket connection;
  bool connected = false;

  AnimationSender(String address, int port)
      : address = address,
        port = port;

  void start() async {
    connection = await Socket.connect(address, port, timeout: Duration(seconds: 10));
    connected = true;
  }

  void end() {
    connection?.destroy();
    connected = false;
  }

  void sendAnimation(AnimationData data) async {
    if (connected) {
      connection.write(data.json());
    } else {
      throw IOException;
    }
  }
}
