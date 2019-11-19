import 'package:animatedledstripclient/src/color_container.dart';

enum Animation {
  COLOR,
  CUSTOMANIMATION,
  CUSTOMREPETITIVEANIMATION,
  ALTERNATE,
  BOUNCE,
  BOUNCETOCOLOR,
  CATTOY,
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

enum Direction { FORWARD, BACKWARD }

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
    return 'DATA:{"animation":"${animation.toString().replaceFirst("Animation.", "")}",'
        '"colors":[${_colorsJson()}],"center":$center,"continuous":$continuous,'
        '"delay":$delay,"delayMod":$delayMod,'
        '"direction":"${direction.toString().replaceFirst("Direction.", "")}",'
        '"distance":$distance,"endPixel":$endPixel,"id":"$id",'
        '"spacing":$spacing,"startPixel":$startPixel}';
  }
}
