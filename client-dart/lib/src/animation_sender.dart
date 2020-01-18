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

import 'dart:convert';
import 'dart:io';
import 'package:animatedledstripclient/src/animation_data.dart';
import 'package:animatedledstripclient/src/running_animation_map.dart';
import 'package:animatedledstripclient/src/strip_info.dart';

class AnimationSender {
  String address;
  int port;
  Socket connection;
  bool connected = false;
  RunningAnimationMap running_animations = RunningAnimationMap();
  StripInfo stripInfo;

  String _partialData = "";

  AnimationSender(String address, int port)
      : address = address,
        port = port;

  void _setStripInfo(String data) {
    var jsonData = jsonDecode(data.substring(5));
    var numLEDs = jsonData['numLEDs'];
    var pin = jsonData['pin'];
    var imageDebugging = jsonData['imageDebugging'];
    var fileName = jsonData['fileName'];
    var rendersBeforeSave = jsonData['rendersBeforeSave'];
    var threadCount = jsonData['threadCount'];
    // non-nullable properties
    if (numLEDs == null || numLEDs.runtimeType != int) return;
    if (imageDebugging == null || imageDebugging.runtimeType != bool) return;
    // nullable properties
    if (pin.runtimeType != int && pin != null) return;
    if (fileName.runtimeType != String && fileName != null) return;
    if (rendersBeforeSave.runtimeType != int && rendersBeforeSave != null) {
      return;
    }
    if (threadCount.runtimeType != int && threadCount != null) return;
    stripInfo = StripInfo(
        jsonData['numLEDs'],
        jsonData['pin'],
        jsonData['imageDebugging'],
        jsonData['fileName'],
        jsonData['rendersBeforeSave'],
        jsonData['threadCount']);
  }

  void receiveData(data) {
    var dataStr = String.fromCharCodes(data).trim();

    if (!dataStr.endsWith(";")) {
      // We are assuming that the lack of a ';' at the end of
      // the data means that the buffer was full and not all
      // data has been received yet.
      // Therefore we will store the data and wait for the
      // next callback before processing the data.
      _partialData += dataStr;
    } else {
      List<String> tokens = (_partialData + dataStr).split(";");
      _partialData = "";

      for (var token in tokens) {
        if (token == "") continue;
        if (token.startsWith("INFO:")) _setStripInfo(token);
        running_animations.put(AnimationData().fromJson(token));
      }
    }
  }

  void start() async {
    connection =
        await Socket.connect(address, port, timeout: Duration(seconds: 10));

    connected = true;
    connection.listen(receiveData);
  }

  void end() {
    connection?.destroy();
    connected = false;
  }

  void sendAnimation(AnimationData data) {
    if (connected) {
      connection.write(data.json());
    } else {
      throw IOException;
    }
  }
}
