import "dart:io";

import "package:args/args.dart";

void main(List<String> args) {
  final parser = ArgParser();

  ArgResults argResults = parser.parse(args);

  List<String> inputStr = new File(argResults.rest[0]).readAsLinesSync();
  List<int> input = inputStr.map(int.parse).toList();
  stdout.writeln(getTwoNumbers(input));
  stdout.writeln(getThreeNumbers(input));
}

int getTwoNumbers(List<int> input) {
  for (int index = 0; index < input.length; index++) {
    for (int indexOther = index + 1; indexOther < input.length; indexOther++) {
      if (input[index] + input[indexOther] == 2020) {
        return input[index] * input[indexOther];
      }
    }
  }
  return -1;
}

int getThreeNumbers(List<int> input) {
  for (int index = 0; index < input.length; index++) {
    for (int indexOther = index + 1; indexOther < input.length; indexOther++) {
      for (int indexAgain = indexOther + 1;
          indexAgain < input.length;
          indexAgain++) {
        if (input[index] + input[indexOther] + input[indexAgain] == 2020) {
          return input[index] * input[indexOther] * input[indexAgain];
        }
      }
    }
  }
  return -1;
}
