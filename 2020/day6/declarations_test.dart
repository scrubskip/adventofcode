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

  Stream<Set<String>> stream = getDeclarationStream(getListAsStream(data));
  getSum(stream).then((value) => expect(value, 11));
}
