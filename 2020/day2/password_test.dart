import "package:test/test.dart";

import "passwords.dart";

void main() {
  test("Basic", _testBasic);
}

void _testBasic() {
  expect(isValid("1-3 a: abcde"), true);
  expect(isValid("1-3 b: cdefg"), false);
  expect(isValid("2-9 c: ccccccccc"), true);
}
