import "dart:io";

import "package:args/args.dart";

void main(List<String> args) {
  final parser = ArgParser();

  ArgResults argResults = parser.parse(args);
  List<String> inputStr = new File(argResults.rest[0]).readAsLinesSync();
  stdout.writeln(inputStr.where(isPositionValid).toList().length);
}

bool isCountValid(String input) {
  return isValid(input, false);
}

bool isPositionValid(String input) {
  return isValid(input, true);
}

bool isValid(String input, bool indexMode) {
  // Parse policy
  List<String> args = input.split(": ");
  String character = args[0].substring(args[0].length - 1);
  String policyStr = args[0].split(" ")[0];
  List<int> policy = policyStr.split("-").map(int.parse).toList();

  String password = args[1];

  // stdout.writeln("Policy: ${character} ${policy}");

  // Check password against policy.
  if (indexMode) {
    return (password[policy[0] - 1] == character) ^
        (password[policy[1] - 1] == character);
  } else {
    int count = password.split("").where((test) => character == test).length;
    return count >= policy[0] && count <= policy[1];
  }
}
