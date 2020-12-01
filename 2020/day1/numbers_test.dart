import "package:test/test.dart";

import "numbers.dart";

void main() {
  test("Simple", _testSimple);
  test("Triple", _testTriple);
}

void _testSimple() {
  List<int> input = [1721, 979, 366, 299, 675, 1456];
  expect(getTwoNumbers(input), 514579);
}

void _testTriple() {
  List<int> input = [1721, 979, 366, 299, 675, 1456];
  expect(getThreeNumbers(input), 241861950);
}
