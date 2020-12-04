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

  // Now do the other pairs.
  expect(
      forest.getMultiple([
        [1, 1],
        [3, 1],
        [5, 1],
        [7, 1],
        [1, 2]
      ]),
      336);
}
