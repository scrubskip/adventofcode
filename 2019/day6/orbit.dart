import 'dart:collection';
import "dart:io";

import "package:args/args.dart";

ArgResults _argResults;

void main(List<String> args) async {
  final parser = ArgParser();

  _argResults = parser.parse(args);

  List<String> input = await new File(_argResults.rest[0]).readAsLines();
  stdout.writeln(getTransfers(input, "YOU", "SAN"));
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

HashMap<String, String> getOrbitMap(List<String> orbitInputs) {
  // Map of node name to parent.
  HashMap<String, String> orbitMap = new HashMap<String, String>();
  for (String orbitString in orbitInputs) {
    List<String> orbit = orbitString.split(")");
    orbitMap[orbit[1]] = orbit[0];
  }
  return orbitMap;
}

List<String> getPath(HashMap<String, String> orbitMap, String name) {
  List<String> output = [];
  String find = name;
  while (orbitMap.containsKey(find)) {
    output.add(find);
    find = orbitMap[find];
  }
  return output.reversed.toList();
}

int getTransfers(List<String> input, String name1, String name2) {
  HashMap<String, String> orbitMap = getOrbitMap(input);
  List<String> path1 = getPath(orbitMap, name1);
  List<String> path2 = getPath(orbitMap, name2);
  if (path1.isEmpty || path2.isEmpty) {
    return 0;
  }
  // Now, remove common elements
  int commonPathIndex = 0;
  while (path1[commonPathIndex] == path2[commonPathIndex]) {
    commonPathIndex++;
  }
  // At this point, they have diverged. The answer is the number of divergences.
  return (path1.length - commonPathIndex) +
      (path2.length - commonPathIndex) -
      2;
}
