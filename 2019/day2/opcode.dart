import "dart:io";

import "package:args/args.dart";

ArgResults _argResults;

void main(List<String> args) {
  final parser = ArgParser();

  _argResults = parser.parse(args);

  Future<String> input = new File(_argResults.rest[0]).readAsString();
  input.then((input_string) => stdout.writeln(
      findNounAndVerb(input_string.split(",").map(int.parse).toList())));
}

int findNounAndVerb(List<int> input) {
  List<int> data = input.toList(growable: false);
  for (int noun = 0; noun < 100; noun++) {
    for (int verb = 0; verb < 100; verb++) {
      List<int> output = run(data, noun, verb);
      if (output[0] == 19690720) {
        return 100 * noun + verb;
      }
    }
  }
  return -1;
}

List<int> run(List<int> input, [int noun, int verb]) {
  int programCounter = 0;
  List<int> data = input.toList(growable: false);
  data[1] = noun != null ? noun : data[1];
  data[2] = verb != null ? verb : data[2];

  while (true) {
    int opcode = data[programCounter];
    if (opcode == 99) break;
    int input1 = data[data[programCounter + 1]];
    int input2 = data[data[programCounter + 2]];
    int outputPos = data[programCounter + 3];
    int output = 0;

    if (opcode == 1) {
      output = input1 + input2;
    } else if (opcode == 2) {
      output = input1 * input2;
    } else {
      throw new ArgumentError("Unknown opcode: $opcode");
    }

    data[outputPos] = output;
    programCounter += 4;
  }

  return data;
}
