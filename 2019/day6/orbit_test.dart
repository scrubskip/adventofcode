import "package:test/test.dart";
import 'orbit.dart';

void main() {
  test("Orbit", _testSimpleOrbit);
  test("Path length", _testPathLength);
}

void _testSimpleOrbit() {
  List<String> orbits = [
    "COM)B",
    "B)C",
    "C)D",
    "D)E",
    "E)F",
    "B)G",
    "G)H",
    "D)I",
    "E)J",
    "J)K",
    "K)L"
  ];

  expect(countOrbits(orbits), 42);
}

void _testPathLength() {
  List<String> orbits = [
    "COM)B",
    "B)C",
    "C)D",
    "D)E",
    "E)F",
    "B)G",
    "G)H",
    "D)I",
    "E)J",
    "J)K",
    "K)L",
    "K)YOU",
    "I)SAN"
  ];

  expect(getTransfers(orbits, "YOU", "SAN"), 4);
}
