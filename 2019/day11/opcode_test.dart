import "package:test/test.dart";
import "dart:io";
import "opcode.dart";

void main() {
  test("Hull", _testHull);
}

void _testHull() {
  Hull hull = new Hull();
  hull.handleOutput(1);
  hull.handleOutput(0);
  expect(hull.currentPosition, Point(-1, 0));
  expect(hull.currentDirection, Direction.WEST);
  expect(hull.getCurrentColor(), 0);
  expect(hull.getColor(Point(0, 0)), 1);
  hull.handleOutput(0);
  hull.handleOutput(0);
  expect(hull.currentPosition, Point(-1, 1));
  expect(hull.currentDirection, Direction.SOUTH);
  expect(hull.getCurrentColor(), 0);
  hull.handleOutput(1);
  hull.handleOutput(0);
  hull.handleOutput(1);
  hull.handleOutput(0);
  expect(hull.currentPosition, Point(0, 0));
  expect(hull.currentDirection, Direction.NORTH);
  expect(hull.getCurrentColor(), 1);
  hull.handleOutput(0);
  hull.handleOutput(1);
  hull.handleOutput(1);
  hull.handleOutput(0);
  hull.handleOutput(1);
  hull.handleOutput(0);
  expect(hull.paintCount, 6);
}
