import "package:test/test.dart";

import "cipher.dart";

void main() {
  test("simple", _testSimple);
}

void _testSimple() {
  List<int> input = [
    35,
    20,
    15,
    25,
    47,
    40,
    62,
    55,
    65,
    95,
    102,
    117,
    150,
    182,
    127,
    219,
    299,
    277,
    309,
    576
  ];
  expect(findFirstInvalid(input, 5), 127);
  expect(findSmallLargeInContiguousRange(input, 127), 62);
}
