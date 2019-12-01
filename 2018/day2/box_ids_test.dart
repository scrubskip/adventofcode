import "package:test/test.dart";

import "box_ids.dart";

void main() {
  List<String> boxIds = [
    "abcdef",
    "bababc",
    "abbcde",
    "abcccd",
    "aabcdd",
    "abcdee",
    "ababab"
  ];
  test("Input data from AOC", () => expect(12, getChecksum(boxIds)));
}
