import "package:test/test.dart";
import "dart:math" as math;
import "asteroid.dart";

void main() {
  test("Simple", testSimple);
  test("Bigger", testBigger);
}

void testSimple() {
  List<String> input = [".#..#", ".....", "#####", "....#", "...##"];
  List<Point> points = parsePoints(input);
  expect(points.length, 10);
  Point greatestPoint = findGreatestPoint(points);
  expect(greatestPoint.x, 3);
  expect(greatestPoint.y, 4);

  Point origin = new Point(0, 0);
  Point up = new Point(0, 1);
  expect(origin.getAngle(up), 0);
  expect(origin.getDistance(up), 1);
  Point right = new Point(1, 0);
  expect(origin.getAngle(right), 90);
  expect(origin.getDistance(right), 1);
  Point down = new Point(0, -1);
  expect(origin.getAngle(down), 180);
  Point left = new Point(-1, 0);
  expect(origin.getAngle(left), 270);
}

void testBigger() {
  List<String> input = [
    "......#.#.",
    "#..#.#....",
    "..#######.",
    ".#.#.###..",
    ".#..#.....",
    "..#....#.#",
    "#..#....#.",
    ".##.#..###",
    "##...#..#.",
    ".#....####"
  ];
  List<Point> points = parsePoints(input);
  expect(points.length, 40);
  Point greatestPoint = findGreatestPoint(points);
  expect(greatestPoint.x, 5);
  expect(greatestPoint.y, 8);
}
