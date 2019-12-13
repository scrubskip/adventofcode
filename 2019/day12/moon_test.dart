import "package:test/test.dart";
import "moon.dart";
import "dart:io";

void main() {
  test("Simple", _testSimple);
  test("Repeat", _testRepeat);
}

void _testSimple() {
  List<String> input = [
    "<x=-1, y=0, z=2>",
    "<x=2, y=-10, z=-7>",
    "<x=4, y=-8, z=8>",
    "<x=3, y=5, z=-1>"
  ];
  List<Moon> moons = input.map(Moon.parse).toList();
  expect(moons.length, 4);
  expect(moons[0].position, [-1, 0, 2]);
  expect(moons[0].velocity, [0, 0, 0]);
  stdout.writeln(moons);
  // Run one step.
  runStep(moons);
  stdout.writeln(moons);
  expect(moons[0].position, [2, -1, 1]);
  expect(moons[0].velocity, [3, -1, -1]);

  runStep(moons);
  stdout.writeln(moons);
  expect(moons[0].position, [5, -3, -1]);
  expect(moons[0].velocity, [3, -2, -2]);

  for (int index = 2; index < 10; index++) {
    runStep(moons);
  }

  expect(getTotalEnergy(moons), 179);
}

void _testRepeat() {
  List<String> input = [
    "<x=-1, y=0, z=2>",
    "<x=2, y=-10, z=-7>",
    "<x=4, y=-8, z=8>",
    "<x=3, y=5, z=-1>"
  ];
  List<Moon> moons = input.map(Moon.parse).toList();
  List<int> steps = getReps(moons);
  stdout.writeln(steps);
}
