import "package:test/test.dart";

import "fuel.dart";

void main() {
  test("Parse", _testParse);
  test("GCD", _testGcd);
  test("Simple", _testSimple);
}

void _testParse() {
  String input = "10 A";
  ReactionActor actor = ReactionActor.parse(input);
  expect(actor.number, 10);
  expect(actor.label, "A");

  String conversionString = "10 ORE => 10 A";
  Conversion conversion = Conversion.parse(conversionString);
  expect(conversion.inputs.length, 1);
  expect(conversion.inputs[0].number, 1);
  expect(conversion.inputs[0].label, "ORE");
  expect(conversion.inputs[0].isOre(), true);
  expect(conversion.output.number, 1);
  expect(conversion.output.label, "A");

  conversionString = "7 A, 1 E => 1 FUEL";
  conversion = Conversion.parse(conversionString);
  expect(conversion.inputs.length, 2);
  expect(conversion.inputs[0].number, 7);
  expect(conversion.inputs[0].label, "A");
  expect(conversion.inputs[1].number, 1);
  expect(conversion.inputs[1].label, "E");
  expect(conversion.output.number, 1);
  expect(conversion.output.label, "FUEL");
  expect(conversion.output.isFuel(), true);
}

void _testGcd() {
  expect(getGcd(2, 6), 2);
  expect(getGcdMultiple([2, 4, 6, 8]), 2);
}

void _testSimple() {
  List<Conversion> conversions =
      ["10 ORE => 1 A", "9 A => 1 FUEL"].map(Conversion.parse).toList();
  expect(getMinOre(conversions), 90);

  conversions =
      ["10 ORE => 2 A", "9 A => 1 FUEL"].map(Conversion.parse).toList();
  expect(getMinOre(conversions), 45);

  conversions = ["10 ORE => 1 A", "11 ORE => 2 B", "1 A, 3 B => 1 FUEL"]
      .map(Conversion.parse)
      .toList();

  expect(getMinOre(conversions), 32);
}
