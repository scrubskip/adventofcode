import "package:test/test.dart";

import "passwords.dart";

void main() {
  test("Basic", _testBasic);
  test("Position", _testPosition);
}

void _testBasic() {
  expect(isCountValid("1-3 a: abcde"), true);
  expect(isCountValid("1-3 b: cdefg"), false);
  expect(isCountValid("2-9 c: ccccccccc"), true);
}

void _testPosition() {
  expect(isPositionValid("1-3 a: abcde"), true);
  expect(isPositionValid("1-3 b: cdefg"), false);
  expect(isPositionValid("2-9 c: ccccccccc"), false);
}
