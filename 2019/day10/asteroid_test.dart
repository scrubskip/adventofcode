import "package:test/test.dart";
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
