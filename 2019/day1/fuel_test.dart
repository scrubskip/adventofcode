import "package:test/test.dart";

import 'fuel.dart';

void main() {
  test("Input simple", _testSimple);
  test("Input list", _testList);
  test("Inclusive", _testInclusive);
}

void _testSimple() {
  expect(2, getFuelRequirement(12));
  expect(2, getFuelRequirement(14));
  expect(654, getFuelRequirement(1969));
}

void _testList() {
  expect(4, getFuelRequirements([12, 14]));
}

void _testInclusive() {
  expect(2, getFuelRequirementInclusive(12));
  expect(2, getFuelRequirementInclusive(14));
  expect(966, getFuelRequirementInclusive(1969));
}
