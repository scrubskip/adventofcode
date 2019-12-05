import "package:test/test.dart";

import 'nanobot.dart';

void main() {
  test("Nanobot class", _testNanobotClass);
  test("Sample input", _testSampleInput);
}

void _testNanobotClass() {
  String input = "pos=<5809333,-2399811,8179851>, r=72498446";
  Nanobot nanobot = Nanobot.parse(input);
  expect(5809333, nanobot.position[0]);
  expect(-2399811, nanobot.position[1]);
  expect(8179851, nanobot.position[2]);
  expect(72498446, nanobot.radius);
}

void _testSampleInput() {
  List<String> input = [
    "pos=<0,0,0>, r=4",
    "pos=<1,0,0>, r=1",
    "pos=<4,0,0>, r=3",
    "pos=<0,2,0>, r=1",
    "pos=<0,5,0>, r=3",
    "pos=<0,0,3>, r=1",
    "pos=<1,1,1>, r=1",
    "pos=<1,1,2>, r=1",
    "pos=<1,3,1>, r=1"
  ];
  List<Nanobot> bots = input.map(Nanobot.parse).toList();
  expect(9, bots.length);
  expect(7, getNeighboringBots(bots));
}
