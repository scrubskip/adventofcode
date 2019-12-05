import "dart:io";

import "package:args/args.dart";

ArgResults _argResults;

void main(List<String> args) {
  final parser = ArgParser();

  _argResults = parser.parse(args);

  Future<String> input = new File(_argResults.rest[0]).readAsString();
  input.then((input_string) => stdout.writeln(
      run(input_string.split(",").map(int.parse).toList(), 1)));
}

List<int> run(List<int> inputCode, [int inputValue]) {
  int programCounter = 0;
  List<int> data = inputCode.toList(growable: false);

  while (true) {
    int rawOpcode = data[programCounter];
    
    // Parse the opcode
    int opcode = rawOpcode % 100;
    int param1Mode = (rawOpcode / 100).floor() % 10;
    int param2Mode = (rawOpcode / 1000).floor() % 10;
    int param3Mode = (rawOpcode / 10000).floor() % 10;
    if (opcode == 99) {
      break;

    }
    int param1Index = param1Mode == 0 ? data[programCounter + 1] : programCounter + 1;
    int outputPos;
    int output;
    int increment = 4;
    if (opcode == 4) {
      increment = 2;
      stdout.writeln(data[param1Index]);
    } else if (opcode == 3) {
      increment = 2;
      outputPos = param1Index;
      data[outputPos] = inputValue;
    } else {
      int param2Index = param2Mode == 0 ? data[programCounter + 2] : programCounter + 2;
      int param3Index = programCounter + 3;
      outputPos = data[param3Index];
      if (opcode == 1) {
        output = data[param1Index] + data[param2Index];
      } else if (opcode == 2) {
        output = data[param1Index] * data[param2Index];
      }
      data[outputPos] = output;
    }
    
    programCounter += increment;
  }

  return data;
}
