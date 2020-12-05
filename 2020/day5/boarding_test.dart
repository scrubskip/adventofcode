import "package:test/test.dart";

import "boarding.dart";

void main() {
  test("Aoc input", _testAocInput);
}

void _testAocInput() {
  expect(getSeatId("FBFBBFFRLR"), 357);
  expect(getSeatId("BFFFBBFRRR"), 567);
  expect(getSeatId("FFFBBBFRRR"), 119);
  expect(getSeatId("BBFFBBFRLL"), 820);
}
