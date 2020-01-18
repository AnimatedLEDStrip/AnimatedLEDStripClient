import 'package:animatedledstripclient/animatedledstripclient.dart';
import 'package:test/test.dart';

void main() {
  test("Default Construction", () {
    var data = AnimationData();
    expect(data.animation, Animation.COLOR);
    expect(data.colors, isEmpty);
    expect(data.center, -1);
    expect(data.continuous, isNull);
    expect(data.delay, -1);
    expect(data.delayMod, 1.0);
    expect(data.direction, Direction.FORWARD);
    expect(data.distance, -1);
    expect(data.endPixel, -1);
    expect(data.id, "");
    expect(data.spacing, -1);
    expect(data.startPixel, 0);
  });

  test("Add Color", () {
    var data = AnimationData();
    expect(data.colors, isEmpty);

    var cc = ColorContainer()
      ..addColor(0xFF)..addColor(0xFF00);
    data.addColor(cc);
    expect(data.colors, isNotEmpty);
    expect(data.colors[0], cc);

    var cc2 = ColorContainer()
      ..addColor(0xFF00FF)..addColor(0xFF0000);
    data.addColor(cc2);
    expect(data.colors[0], cc);
    expect(data.colors[1], cc2);
  });

  test("JSON", () {
    var cc1 = ColorContainer()
      ..addColor(0xFF)..addColor(0xFF00);
    var cc2 = ColorContainer()
      ..addColor(0xFF0000);

    var data = AnimationData()
      ..animation = Animation.METEOR
      ..addColor(cc1)..addColor(cc2)
      ..center = 50
      ..continuous = false
      ..delay = 10
      ..delayMod = 1.5
      ..direction = Direction.BACKWARD
      ..distance = 45
      ..endPixel = 200
      ..id = "TEST"
      ..spacing = 5
      ..startPixel = 15;

    expect(data.json(),
        'DATA:{"animation":"METEOR","colors":[{"colors":[255, 65280]},{"colors":[16711680]}],"center":50,"continuous":false,"delay":10,"delayMod":1.5,"direction":"BACKWARD","distance":45,"endPixel":200,"id":"TEST","spacing":5,"startPixel":15}');
  });

  test("JSON No Colors", () {
    var data = AnimationData();

    expect(data.json(),
        'DATA:{"animation":"COLOR","colors":[],"center":-1,"continuous":null,"delay":-1,"delayMod":1.0,"direction":"FORWARD","distance":-1,"endPixel":-1,"id":"","spacing":-1,"startPixel":0}');
  });

  test("From JSON", () {
    var dataStr = 'DATA:{"animation":"METEOR","colors":[{"colors":[255, 65280]},{"colors":[16711680]}],"center":50,"continuous":false,"delay":10,"delayMod":1.5,"direction":"BACKWARD","distance":45,"endPixel":200,"id":"TEST","spacing":5,"startPixel":15}';
    
    var data = AnimationData().fromJson(dataStr);
    
    expect(data.animation, Animation.METEOR);
    expect(data.colors.length, 2);
    expect(data.colors[0].colors.length, 2);
    expect(data.colors[0].colors[0], 0xFF);
    expect(data.colors[0].colors[1], 0xFF00);
    expect(data.colors[1].colors.length, 1);
    expect(data.colors[1].colors[0], 0xFF0000);
    expect(data.center, 50);
    expect(data.continuous, false);
    expect(data.delay, 10);
    expect(data.delayMod, 1.5);
    expect(data.direction, Direction.BACKWARD);
    expect(data.distance, 45);
    expect(data.endPixel, 200);
    expect(data.id, "TEST");
    expect(data.spacing, 5);
    expect(data.startPixel, 15);
  });

  test("Animation from String", (){
    expect(animationFromString("COLOR"), Animation.COLOR);
    expect(animationFromString("CUSTOMANIMATION"), Animation.CUSTOMANIMATION);
    expect(animationFromString("CUSTOMREPETITIVEANIMATION"), Animation.CUSTOMREPETITIVEANIMATION);
    expect(animationFromString("ALTERNATE"), Animation.ALTERNATE);
    expect(animationFromString("BOUNCE"), Animation.BOUNCE);
    expect(animationFromString("BOUNCETOCOLOR"), Animation.BOUNCETOCOLOR);
    expect(animationFromString("CATTOY"), Animation.CATTOY);
    expect(animationFromString("METEOR"), Animation.METEOR);
    expect(animationFromString("MULTIPIXELRUN"), Animation.MULTIPIXELRUN);
    expect(animationFromString("MULTIPIXELRUNTOCOLOR"), Animation.MULTIPIXELRUNTOCOLOR);
    expect(animationFromString("RIPPLE"), Animation.RIPPLE);
    expect(animationFromString("PIXELMARATHON"), Animation.PIXELMARATHON);
    expect(animationFromString("PIXELRUN"), Animation.PIXELRUN);
    expect(animationFromString("SMOOTHCHASE"), Animation.SMOOTHCHASE);
    expect(animationFromString("SMOOTHFADE"), Animation.SMOOTHFADE);
    expect(animationFromString("SPARKLE"), Animation.SPARKLE);
    expect(animationFromString("SPARKLEFADE"), Animation.SPARKLEFADE);
    expect(animationFromString("SPARKLETOCOLOR"), Animation.SPARKLETOCOLOR);
    expect(animationFromString("SPLAT"), Animation.SPLAT);
    expect(animationFromString("STACK"), Animation.STACK);
    expect(animationFromString("STACKOVERFLOW"), Animation.STACKOVERFLOW);
    expect(animationFromString("WIPE"), Animation.WIPE);
    expect(animationFromString("ENDANIMATION"), Animation.ENDANIMATION);
    expect(animationFromString("NONCOLOR"), Animation.COLOR);
  });

  test("Direction from String", () {
    expect(directionFromString("FORWARD"), Direction.FORWARD);
    expect(directionFromString("BACKWARD"), Direction.BACKWARD);
    expect(directionFromString("NONDIRECTION"), Direction.FORWARD);
  });

  test("Center from JSON", () {
    var dataStr = 'DATA:{"center":10}';
    var data = AnimationData().fromJson(dataStr);
    expect(data.center, 10);

    var dataStr2 = 'DATA:{}';
    var data2 = AnimationData().fromJson(dataStr2);
    expect(data2.center, -1);
  });

  test("Continuous from JSON", () {
    var dataStr = 'DATA:{"continuous":true}';
    var data = AnimationData().fromJson(dataStr);
    expect(data.continuous, true);

    var dataStr2 = 'DATA:{"continuous":false}';
    var data2 = AnimationData().fromJson(dataStr2);
    expect(data2.continuous, false);

    var dataStr3 = 'DATA:{"continuous":null}';
    var data3 = AnimationData().fromJson(dataStr3);
    expect(data3.continuous, null);

    var dataStr4 = 'DATA:{}';
    var data4 = AnimationData().fromJson(dataStr4);
    expect(data4.continuous, null);
  });

  test("Delay from JSON", () {
    var dataStr = 'DATA:{"delay":10}';
    var data = AnimationData().fromJson(dataStr);
    expect(data.delay, 10);

    var dataStr2 = 'DATA:{}';
    var data2 = AnimationData().fromJson(dataStr2);
    expect(data2.delay, -1);
  });

  test("DelayMod from JSON", () {
    var dataStr = 'DATA:{"delayMod":2.0}';
    var data = AnimationData().fromJson(dataStr);
    expect(data.delayMod, 2.0);

    var dataStr2 = 'DATA:{}';
    var data2 = AnimationData().fromJson(dataStr2);
    expect(data2.delayMod, 1.0);
  });

  test("Distance from JSON", () {
    var dataStr = 'DATA:{"distance":10}';
    var data = AnimationData().fromJson(dataStr);
    expect(data.distance, 10);

    var dataStr2 = 'DATA:{}';
    var data2 = AnimationData().fromJson(dataStr2);
    expect(data2.distance, -1);
  });

  test("EndPixel from JSON", () {
    var dataStr = 'DATA:{"endPixel":10}';
    var data = AnimationData().fromJson(dataStr);
    expect(data.endPixel, 10);

    var dataStr2 = 'DATA:{}';
    var data2 = AnimationData().fromJson(dataStr2);
    expect(data2.endPixel, -1);
  });

  test("ID from JSON", () {
    var dataStr = 'DATA:{"id":"TEST"}';
    var data = AnimationData().fromJson(dataStr);
    expect(data.id, "TEST");

    var dataStr2 = 'DATA:{}';
    var data2 = AnimationData().fromJson(dataStr2);
    expect(data2.id, "");
  });

  test("Spacing from JSON", () {
    var dataStr = 'DATA:{"spacing":10}';
    var data = AnimationData().fromJson(dataStr);
    expect(data.spacing, 10);

    var dataStr2 = 'DATA:{}';
    var data2 = AnimationData().fromJson(dataStr2);
    expect(data2.spacing, -1);
  });

  test("StartPixel from JSON", () {
    var dataStr = 'DATA:{"startPixel":10}';
    var data = AnimationData().fromJson(dataStr);
    expect(data.startPixel, 10);

    var dataStr2 = 'DATA:{}';
    var data2 = AnimationData().fromJson(dataStr2);
    expect(data2.startPixel, 0);
  });
}
