import "dart:io";

import "package:args/args.dart";

ArgResults _argResults;

void main(List<String> args) {
  final parser = ArgParser();

  _argResults = parser.parse(args);

  Future<List<String>> lineList = new File(_argResults.rest[0]).readAsLines();

  lineList.then((list) =>
      stdout.writeln(getFuelRequirements(list.map(int.parse).toList(), true)));
}

int getFuelRequirements(List<int> input, [bool inclusive = false]) {
  return input
      .map(inclusive ? getFuelRequirementInclusive : getFuelRequirement)
      .reduce((x, y) => x + y);
}

int getFuelRequirement(int mass) {
  return (mass / 3).floor() - 2;
}

int getFuelRequirementInclusive(int mass) {
  int total = 0;
  int fuel = getFuelRequirement(mass);
  while (fuel > 0) {
    total += fuel;
    fuel = getFuelRequirement(fuel);
  }
  return total;
}
