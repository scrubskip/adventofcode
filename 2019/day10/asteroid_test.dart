import "package:test/test.dart";
import "dart:io";
import "asteroid.dart";

void main() {
  test("Simple", testSimple);
  //test("Bigger", testBigger);
  //test("Points by angle", testPointsByAngle);
  //test("Vaporization", testVaporizationSimple);
}

void testSimple() {
  List<String> input = [".#..#", ".....", "#####", "....#", "...##"];
  List<Point> points = parsePoints(input);
  expect(points.length, 10);
  Point greatestPoint = findGreatestPoint(points);
  expect(greatestPoint, Point(3, 4));

  Point origin = new Point(1, 1);
<<<<<<< HEAD
  Point up = new Point(1, 0);
  expect(origin.getAngleInDegrees(up), 0);
  expect(origin.getDistance(up), 1);
  Point right = new Point(2, 1);
  expect(origin.getAngleInDegrees(right), 90);
  expect(origin.getDistance(right), 1);
  Point down = new Point(1, 2);
  expect(origin.getAngleInDegrees(down), 180);
=======
  Point up = new Point(0, 1);
  expect(origin.getAngleInDegrees(up), 180);
  expect(origin.getDistance(up), 1);
  Point right = new Point(2, 1);
  expect(origin.getAngleInDegrees(right), 0);
  expect(origin.getDistance(right), 1);
  Point down = new Point(1, 2);
  expect(origin.getAngleInDegrees(down), 90);
>>>>>>> 90c2ce1bc1ce76df34842c16cf01f521c363e81f
  Point left = new Point(0, 1);
  expect(origin.getAngleInDegrees(left), 270);
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

void testPointsByAngle() {
  List<String> input = [".#.", ".#.", ".#.", "...", ".#."];
  List<Point> points = parsePoints(input);
  Point greatestPoint = findGreatestPoint(points);
  expect(greatestPoint, Point(1, 1));
  List<Point> vaporizationOrder = getVaporizationOrder(points, greatestPoint);
  expect(points.length - 1, vaporizationOrder.length);
  expect(vaporizationOrder, [Point(1, 0), Point(1, 2), Point(1, 4)]);
}

void testVaporizationSimple() {
  List<String> input = [
    ".#....#####...#..",
    "##...##.#####..##",
    "##...#...#.#####.",
    "..#.....#...###..",
    "..#.#.....#....##"
  ];
  List<Point> points = parsePoints(input);
  Point greatestPoint = findGreatestPoint(points);
  expect(greatestPoint, Point(8, 3));
  List<Point> vaporizationOrder = getVaporizationOrder(points, greatestPoint);
  expect(points.length - 1, vaporizationOrder.length);
  stdout.writeln(vaporizationOrder);
}
