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

    var cc = ColorContainer()..addColor(0xFF)..addColor(0xFF00);
    data.addColor(cc);
    expect(data.colors, isNotEmpty);
    expect(data.colors[0], cc);

    var cc2 = ColorContainer()..addColor(0xFF00FF)..addColor(0xFF0000);
    data.addColor(cc2);
    expect(data.colors[0], cc);
    expect(data.colors[1], cc2);
  });

  test("JSON", () {
    var cc1 = ColorContainer()..addColor(0xFF)..addColor(0xFF00);
    var cc2 = ColorContainer()..addColor(0xFF0000);

    var data = AnimationData()
      ..animation = Animation.METEOR
      ..addColor(cc1)
      ..addColor(cc2)
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
}
