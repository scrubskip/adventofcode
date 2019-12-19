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
  // Get reactants
  Map<String, int> input = new Map<String, int>();
  input["FUEL"] = 1;
  Map<String, int> reactants = expandReactants(input, conversionMap);
  while (reactants.length > 1 || reactants["ORE"] == null) {
    reactants = expandReactants(reactants, conversionMap);
  }
  // Now we should only have "ORE" in here.
  return reactants["ORE"];
}

Map<String, int> expandReactants(
    Map<String, int> reactants, Map<String, Conversion> conversionMap) {
  var reactantMap = new Map<String, int>();
  for (String label in reactants.keys) {
    if (label == "ORE") {
      if (reactantMap.containsKey("ORE")) {
        reactantMap["ORE"] += reactants["ORE"];
      } else {
        reactantMap["ORE"] = reactants["ORE"];
      }
    } else {
      Conversion conversion = conversionMap[label];
      for (ReactionActor actor in conversion.inputs) {
        int desiredQuantity =
            (actor.number * reactants[label] / conversion.output.number).ceil();
        if (reactantMap.containsKey(actor.label)) {
          reactantMap[actor.label] += desiredQuantity;
        } else {
          reactantMap[actor.label] = desiredQuantity;
        }
      }
    }
  }
  // Now for each label, adjust the map based on the conversion outputs.
  for (String label in reactantMap.keys) {
    if (label == "ORE") {
      continue;
    }
    Conversion conversion = conversionMap[label];
    reactantMap[label] =
        minRequired(conversion.output.number, reactantMap[label]);
  }
  return reactantMap;
}

int getOreCount(ReactionActor actor, Map<String, Conversion> conversionMap,
    [int desiredQuantity = 1]) {
  Conversion conversion = conversionMap[actor.label];
  desiredQuantity = minRequired(conversion.output.number, desiredQuantity);
  List<ReactionActor> inputActors = conversion.inputs;

  if (inputActors.length == 1 && inputActors[0].isOre()) {
    return (inputActors[0].number * desiredQuantity / conversion.output.number)
        .floor();
  }
  // Otherwise, go searching.
  int oreCount = 0;
  for (ReactionActor actor in inputActors) {
    // Create a new actor with the scale
    oreCount +=
        getOreCount(actor, conversionMap, actor.number * desiredQuantity);
  }
  return oreCount;
}

int minRequired(int actorNumber, int desiredQuantity) {
  int output = actorNumber;
  while (output < desiredQuantity) {
    output += actorNumber;
  }
  return output;
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
