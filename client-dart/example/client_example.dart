/*
 *  Copyright (c) 2019-2020 AnimatedLEDStrip
 *  
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

import 'package:animatedledstripclient/animatedledstripclient.dart';

main() async {
  var c = ColorContainer()..addColor(0xFF0000);
  var data = AnimationData()
    ..animation = Animation.METEOR
    ..addColor(c);

  var s = AnimationSender("10.44.36.53", 5);
  await s.start();
  await s.sendAnimation(data);
  await Future.delayed(Duration(seconds: 4));
  print(s.stripInfo.numLEDs);
  print(s.stripInfo.pin);
  print(s.stripInfo.imageDebugging);
  print(s.stripInfo.fileName);
  print(s.stripInfo.rendersBeforeSave);
  print(s.stripInfo.threadCount);
  var i;
  for (i in await s.running_animations.ids()) {
    print(i);
  }
  s.end();
}
