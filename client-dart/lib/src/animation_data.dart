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

import 'package:animatedledstripclient/src/color_container.dart';
import 'dart:convert';

enum Animation {
  COLOR,
  CUSTOMANIMATION,
  CUSTOMREPETITIVEANIMATION,
  ALTERNATE,
  BOUNCE,
  BOUNCETOCOLOR,
  CATTOY,
  CATTOYTOCOLOR,
  FADETOCOLOR,
  FIREWORKS,
  METEOR,
  MULTIPIXELRUN,
  MULTIPIXELRUNTOCOLOR,
  RIPPLE,
  PIXELMARATHON,
  PIXELRUN,
  SMOOTHCHASE,
  SMOOTHFADE,
  SPARKLE,
  SPARKLEFADE,
  SPARKLETOCOLOR,
  SPLAT,
  STACK,
  STACKOVERFLOW,
  WIPE,
  ENDANIMATION
}

Animation animationFromString(String anim) {
  switch (anim?.toUpperCase()) {
    case "COLOR":
      return Animation.COLOR;
    case "CUSTOMANIMATION":
      return Animation.CUSTOMANIMATION;
    case "CUSTOMREPETITIVEANIMATION":
      return Animation.CUSTOMREPETITIVEANIMATION;
    case "ALTERNATE":
      return Animation.ALTERNATE;
    case "BOUNCE":
      return Animation.BOUNCE;
    case "BOUNCETOCOLOR":
      return Animation.BOUNCETOCOLOR;
    case "CATTOY":
      return Animation.CATTOY;
    case "CATTOYTOCOLOR":
      return Animation.CATTOYTOCOLOR;
    case "FADETOCOLOR":
      return Animation.FADETOCOLOR;
    case "FIREWORKS":
      return Animation.FIREWORKS;
    case "METEOR":
      return Animation.METEOR;
    case "MULTIPIXELRUN":
      return Animation.MULTIPIXELRUN;
    case "MULTIPIXELRUNTOCOLOR":
      return Animation.MULTIPIXELRUNTOCOLOR;
    case "RIPPLE":
      return Animation.RIPPLE;
    case "PIXELMARATHON":
      return Animation.PIXELMARATHON;
    case "PIXELRUN":
      return Animation.PIXELRUN;
    case "SMOOTHCHASE":
      return Animation.SMOOTHCHASE;
    case "SMOOTHFADE":
      return Animation.SMOOTHFADE;
    case "SPARKLE":
      return Animation.SPARKLE;
    case "SPARKLEFADE":
      return Animation.SPARKLEFADE;
    case "SPARKLETOCOLOR":
      return Animation.SPARKLETOCOLOR;
    case "SPLAT":
      return Animation.SPLAT;
    case "STACK":
      return Animation.STACK;
    case "STACKOVERFLOW":
      return Animation.STACKOVERFLOW;
    case "WIPE":
      return Animation.WIPE;
    case "ENDANIMATION":
      return Animation.ENDANIMATION;
    default:
      return Animation.COLOR;
  }
}

enum Direction { FORWARD, BACKWARD }

Direction directionFromString(String dir) {
  switch (dir?.toUpperCase()) {
    case "FORWARD":
      return Direction.FORWARD;
    case "BACKWARD":
      return Direction.BACKWARD;
    default:
      return Direction.FORWARD;
  }
}

class AnimationData {
  Animation animation = Animation.COLOR;
  List<ColorContainer> colors = [];
  int center = -1;
  bool continuous;
  int delay = -1;
  double delayMod = 1.0;
  Direction direction = Direction.FORWARD;
  int distance = -1;
  int endPixel = -1;
  String id = "";
  int spacing = -1;
  int startPixel = 0;

  void addColor(ColorContainer c) {
    colors.add(c);
  }

  String _colorsJson() {
    if (colors.isEmpty) {
      return "";
    } else {
      String cols = "";
      for (ColorContainer c in colors) {
        cols += c.json();
        cols += ",";
      }
      return cols.substring(0, cols.length - 1);
    }
  }

  String json() {
    return 'DATA:{"animation":"${animation.toString().replaceFirst(
        "Animation.", "")}",'
        '"colors":[${_colorsJson()}],"center":$center,"continuous":$continuous,'
        '"delay":$delay,"delayMod":$delayMod,'
        '"direction":"${direction.toString().replaceFirst("Direction.", "")}",'
        '"distance":$distance,"endPixel":$endPixel,"id":"$id",'
        '"spacing":$spacing,"startPixel":$startPixel}';
  }

  AnimationData fromJson(String data) {
    var jsonData = jsonDecode(data.substring(5));

    animation = animationFromString(jsonData["animation"]);
    if (jsonData["colors"] != null) {
      for (var ccStr in jsonData["colors"]) {
        var cc = ColorContainer();
        if (ccStr["colors"] != null) {
          for (var col in ccStr["colors"]) {
            cc.addColor(col);
          }
        }
        addColor(cc);
      }
    }
    center = jsonData["center"] ?? -1;
    continuous = jsonData["continuous"];
    delay = jsonData["delay"] ?? -1;
    delayMod = jsonData["delayMod"] ?? 1.0;
    direction = directionFromString(jsonData["direction"]);
    distance = jsonData["distance"] ?? -1;
    endPixel = jsonData["endPixel"] ?? -1;
    id = jsonData["id"] ?? "";
    spacing = jsonData["spacing"] ?? -1;
    startPixel = jsonData["startPixel"] ?? 0;
    
    return this;
  }
}
