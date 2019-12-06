import 'dart:collection';
import "dart:io";

import "package:args/args.dart";

ArgResults _argResults;

void main(List<String> args) async {
  final parser = ArgParser();

  _argResults = parser.parse(args);

  List<String> input = await new File(_argResults.rest[0]).readAsLines();
  stdout.writeln(countOrbits(input));
}

int countOrbits(List<String> orbitInputs) {
  // Map of node name to parent.
  HashMap<String, String> orbitMap = new HashMap<String, String>();
  Set<String> nodeNames = new Set<String>();
  for (String orbitString in orbitInputs) {
    List<String> orbit = orbitString.split(")");
    nodeNames.add(orbit[0]);
    nodeNames.add(orbit[1]);
    orbitMap[orbit[1]] = orbit[0];
  }
  // Now loop through the node names.
  int total = 0;
  nodeNames.forEach((name) => {total += getOrbitCount(orbitMap, name)});

  return total;
}

int getOrbitCount(HashMap<String, String> orbitMap, String name) {
  String find = name;
  int count = 0;
  while (orbitMap.containsKey(find)) {
    find = orbitMap[find];
    count++;
  }
  return count;
}
