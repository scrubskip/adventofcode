import "package:test/test.dart";

import "toboggan.dart";

void main() {
  test("AoC input", _testAocInput);
}

void _testAocInput() {
  Forest forest = new Forest([
    "..##.......",
    "#...#...#..",
    ".#....#..#.",
    "..#.#...#.#",
    ".#...##..#.",
    "..#.##.....",
    ".#.#.#....#",
    ".#........#",
    "#.##...#...",
    "#...##....#",
    ".#..#...#.#"
  ]);
  expect(forest.getTreeCount(3, 1), 7);
}
