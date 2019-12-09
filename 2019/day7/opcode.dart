import "dart:io";

import "package:args/args.dart";

ArgResults _argResults;

void main(List<String> args) {
  final parser = ArgParser();

  _argResults = parser.parse(args);

  Future<String> input = new File(_argResults.rest[0]).readAsString();
  input.then((input_string) => stdout
      .writeln(findMaxValue(input_string.split(",").map(int.parse).toList())));
}

int findMaxValue(List<int> inputCode) {
  int maxSignal = 0;
  List<int> maxPhase = [];
  for (int a = 0; a < 5; a++) {
    for (int b = 0; b < 5; b++) {
      if (b == a) {
        continue;
      }
      for (int c = 0; c < 5; c++) {
        if (c == b || c == a) {
          continue;
        }
        for (int d = 0; d < 5; d++) {
          if (d == c || d == b || d == a) {
            continue;
          }
          for (int e = 0; e < 5; e++) {
            if (e == d || e == c || e == b || e == a) {
              continue;
            }
            List<int> input = [a, b, c, d, e];
            int value = getAmpSignal(inputCode, input);
            if (value > maxSignal) {
              maxSignal = value;
              maxPhase = input;
            }
          }
        }
      }
    }
  }
  stdout.writeln("max phase $maxPhase at $maxSignal");
  return maxSignal;
}

int getAmpSignal(List<int> inputCode, List<int> phaseSignals) {
  int inputSignal = 0;
  phaseSignals.forEach((phaseSignal) {
    List<int> output = run(inputCode, [phaseSignal, inputSignal], true);
    inputSignal = output[0];
    // stdout.writeln("Output: $output");
  });
  return inputSignal;
}

List<int> run(List<int> inputCode,
    [List<int> inputValue, bool returnOutput = false]) {
  // stdout.writeln("Running $inputValue");
  int programCounter = 0;
  List<int> data = inputCode.toList(growable: false);
  List<int> programOutput = [];
  int inputValueIndex = 0;
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
        param1Mode == 0 ? data[programCounter + 1] : programCounter + 1;
    int outputPos;
    int output;
    int increment = 4;
    if (opcode == 4) {
      increment = 2;
      programOutput.add(data[param1Index]);
      // stdout.writeln(data[param1Index]);
    } else if (opcode == 3) {
      increment = 2;
      outputPos = param1Index;
      data[outputPos] = inputValue[inputValueIndex];
      inputValueIndex = (inputValueIndex + 1) % inputValue.length;
    } else {
      int param2Index =
          param2Mode == 0 ? data[programCounter + 2] : programCounter + 2;
      int param3Index = programCounter + 3;
      outputPos = data[param3Index];
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
      if (output != null) data[outputPos] = output;
    }

    programCounter += increment;
  }
  if (returnOutput) {
    return programOutput;
  } else {
    return data;
  }
}
