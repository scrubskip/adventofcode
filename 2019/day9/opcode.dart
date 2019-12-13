import 'dart:collection';
import "dart:io";

import "package:args/args.dart";

ArgResults _argResults;

void main(List<String> args) {
  final parser = ArgParser();

  _argResults = parser.parse(args);

  Future<String> input = new File(_argResults.rest[0]).readAsString();
  Queue<int> inputQueue = new Queue<int>();
  inputQueue.add(1);
  input.then((input_string) => run(
      input_string.split(",").map(int.parse).toList(),
      1,
      (output) => stdout.writeln("$output")));
}

List<int> run(List<int> inputCode, [int input, Function(int) onOutput]) {
  // stdout.writeln("Running $inputValue");
  int programCounter = 0;
  int relativeBase = 0;
  List<int> data = inputCode.toList();
  data.addAll(List<int>.generate(1000, (index) => 0));

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
    int param1Index =
        getParamIndex(param1Mode, programCounter + 1, data, relativeBase);
    int outputPos;
    int output;
    int increment = 4;
    if (opcode == 4) {
      increment = 2;
      if (onOutput != null) {
        onOutput(data[param1Index]);
      }
    } else if (opcode == 3) {
      increment = 2;
      outputPos = param1Index;
      if (input != null) {
        output = input;
        data[outputPos] = output;
      } else {
        throw new StateError("Asked for input but no input given.");
      }
    } else if (opcode == 9) {
      increment = 2;
      relativeBase += data[param1Index];
    } else {
      int param2Index =
          getParamIndex(param2Mode, programCounter + 2, data, relativeBase);
      int param3Index =
          getParamIndex(param3Mode, programCounter + 3, data, relativeBase);
      if (opcode == 1) {
        output = data[param1Index] + data[param2Index];
      } else if (opcode == 2) {
        output = data[param1Index] * data[param2Index];
      } else if (opcode == 5 || opcode == 6) {
        increment = 3;
        if ((opcode == 5 && data[param1Index] != 0) ||
            (opcode == 6 && data[param1Index] == 0)) {
          increment = 0;
          programCounter = data[param2Index];
        }
      } else if (opcode == 7 || opcode == 8) {
        if ((opcode == 7 && data[param1Index] < data[param2Index]) ||
            (opcode == 8 && data[param1Index] == data[param2Index])) {
          output = 1;
        } else {
          output = 0;
        }
      }
      if (output != null) {
        if (outputPos == null) {
          // Might have been set already, don't reset it.
          outputPos = param3Index;
        }
        data[outputPos] = output;
      }
    }

    programCounter += increment;
  }
  return data;
}

int getParamIndex(int mode, int startIndex, List<int> data, int relativeBase) {
  switch (mode) {
    case 0:
      return data[startIndex];
    case 1:
      return startIndex;
    case 2:
      return relativeBase + data[startIndex];
    default:
      throw new ArgumentError("Unknown mode $mode");
  }
}
