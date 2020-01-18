import 'package:animatedledstripclient/animatedledstripclient.dart';
import 'package:test/test.dart';

void main() {
  test("Default Construction", () {
    var cc = ColorContainer();
    expect(cc.colors, isEmpty);
  });

  test("Add Color", () {
    var cc = ColorContainer();
    expect(cc.colors, isEmpty);
    cc.addColor(0xFF);
    cc.addColor(0xFF00);
    expect(cc.colors, isNotEmpty);
    expect(cc.colors[0], 0xFF);
    expect(cc.colors[1], 0xFF00);
  });

  test("JSON", () {
    var cc = ColorContainer()
        ..addColor(0xFF0000)
        ..addColor(0xFF00);

    expect('{"colors":[16711680, 65280]}', cc.json());
    
    var cc2 = ColorContainer();
    expect('{"colors":[]}', cc2.json());
  });
}