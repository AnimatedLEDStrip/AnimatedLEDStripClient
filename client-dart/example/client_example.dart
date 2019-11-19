import 'dart:io';

import 'package:animatedledstripclient/client.dart';

main() async {
  var c = ColorContainer()..addColor(0xFF00)..addColor(0xFFFF);
  var data = AnimationData()
    ..animation = Animation.RIPPLE
    ..center = 50
    ..addColor(c);

  print(data.json());

  var s = AnimationSender("10.44.157.2", 5);
  await s.start();
  s.sendAnimation(data);
  sleep(Duration(seconds: 15));
  s.end();
}
