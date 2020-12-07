import "package:test/test.dart";

import "declarations.dart";

void main() {
  test("test aoc input", _testAocInput);
}

void _testAocInput() {
  var data = [
    "abc",
    "",
    "a",
    "b",
    "c",
    "",
    "ab",
    "ac",
    "",
    "a",
    "a",
    "a",
    "a",
    "",
    "b"
  ];

  Stream<Map<String, int>> stream = getDeclarationStream(getListAsStream(data));
  getSum(stream).then((value) => expect(value, 11));

  stream = getDeclarationStream(getListAsStream(data));
  getUnanimousSum(stream).then((value) => expect(value, 6));
}
