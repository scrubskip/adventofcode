import "dart:io";

import "package:args/args.dart";

ArgResults _argResults;

void main(List<String> args) async {
  final parser = ArgParser();

  _argResults = parser.parse(args);
}

int getMinOre(List<Conversion> conversions) {
  // First, put these into a map keyed by output.
  var conversionMap = new Map<String, Conversion>();
  conversions.forEach(
      (conversion) => conversionMap[conversion.output.label] = conversion);
  // Now find the fuel.
  var fuelConversion = conversionMap["FUEL"];
  if (fuelConversion == null) {
    throw new ArgumentError("No fuel output exists.");
  }
  if (fuelConversion.output.number != 1) {
    throw new ArgumentError(
        "Fuel conversion != 1. ${fuelConversion.output.number}");
  }
  return getOreCount(fuelConversion.output, conversionMap).ceil();
}

double getOreCount(ReactionActor actor, Map<String, Conversion> conversionMap) {
  Conversion conversion = conversionMap[actor.label];
  List<ReactionActor> inputActors = conversion.inputs;

  if (inputActors.length == 1 && inputActors[0].isOre()) {
    return inputActors[0].number * actor.number / conversion.output.number;
  }
  // Otherwise, go searching.
  double oreCount = 0;
  for (ReactionActor actor in inputActors) {
    oreCount += getOreCount(actor, conversionMap) * conversion.output.number;
  }
  return oreCount * actor.number;
}

int getGcdMultiple(List<int> inputs) {
  int gcd = getGcd(inputs[0], inputs[1]);
  return inputs.skip(1).fold(gcd, (x, y) => getGcd(x, y));
}

int getGcd(int a, int b) {
  int t;
  while (b != 0) {
    t = b;
    b = a % b;
    a = t;
  }
  return a;
}

class Conversion {
  List<ReactionActor> inputs;
  ReactionActor output;

  static Conversion parse(String conversionAsString) {
    List<String> conversionParts = conversionAsString.split(" => ");
    // First part of this string is comma separated
    List<String> inputs = conversionParts[0].split(",");
    String output = conversionParts[1];
    // Now create actors for each thing.
    Conversion conversion = Conversion();
    conversion.output = ReactionActor.parse(output);
    conversion.inputs = inputs.map(ReactionActor.parse).toList();

    // Reduce to GCD
    List<int> factors =
        conversion.inputs.map((conversion) => conversion.number).toList();
    factors.add(conversion.output.number);
    int gcd = getGcdMultiple(factors);
    if (gcd > 1) {
      // Divide all by the number.
      conversion.inputs.forEach((conversion) =>
          conversion.number = (conversion.number / gcd).floor());
      conversion.output.number = (conversion.output.number / gcd).floor();
    }

    return conversion;
  }
}

class ReactionActor {
  int number;
  String label;

  ReactionActor(this.number, this.label);

  static ReactionActor parse(String actorString) {
    List<String> parts = actorString.trim().split(" ");
    return ReactionActor(int.tryParse(parts[0]), parts[1]);
  }

  bool isOre() {
    return label == "ORE";
  }

  bool isFuel() {
    return label == "FUEL";
  }
}
