import 'dart:convert';
import 'dart:io';

import "package:args/args.dart";

void main(List<String> args) {
  final parser = ArgParser();
  ArgResults argResults = parser.parse(args);
  List<String> inputStr = new File(argResults.rest[0]).readAsLinesSync();
  List<Instruction> input = inputStr.map(Instruction.parse).toList();
  stdout.writeln(fixProgram(input));
}

void runProgram(
    List<Instruction> input, bool onInstruction(int counter, int accumulator)) {
  int accumulator = 0;
  int counter = 0;
  while (counter < input.length) {
    Instruction instruction = input[counter];
    switch (instruction.name) {
      case "acc":
        accumulator += instruction.value;
        break;
      case "jmp":
        counter +=
            instruction.value - 1; // it will advance next, so remove one.
        break;
      case "nop":
        break;
    }
    counter++;
    if (!onInstruction(counter, accumulator)) {
      break;
    }
  }
}

int findLoop(List<Instruction> input) {
  Set<int> seenCounts = new Set();
  int lastAccumulator = 0;
  runProgram(input, (counter, accumulator) {
    if (seenCounts.contains(counter)) {
      lastAccumulator = accumulator;
      return false;
    }
    seenCounts.add(counter);
    return true;
  });
  return lastAccumulator;
}

int hasLoop(List<Instruction> input) {
  Set<int> seenCounts = new Set();
  int lastAccumulator = 0;
  bool foundLoop = false;
  runProgram(input, (counter, accumulator) {
    lastAccumulator = accumulator;
    if (seenCounts.contains(counter)) {
      foundLoop = true;
      return false;
    }
    seenCounts.add(counter);
    return true;
  });
  if (foundLoop) {
    return -1;
  }
  return lastAccumulator;
}

int fixProgram(List<Instruction> input) {
  int alteredIndex = -1;
  int lastAccumulator = -1;
  while ((lastAccumulator = hasLoop(input)) == -1) {
    // Reset the instruction
    if (alteredIndex >= 0) {
      input[alteredIndex].flip();
    }
    // Find the next input to flip
    alteredIndex = input.indexWhere(Instruction.isFlippable, alteredIndex + 1);
    if (alteredIndex != -1) {
      input[alteredIndex].flip();
    } else {
      throw new Exception("No flipped index found");
    }
  }
  return lastAccumulator;
}

class Instruction {
  String name;
  int value;

  Instruction(this.name, this.value);

  static Instruction parse(String input) {
    List<String> parts = input.split(" ");
    return Instruction(parts[0], int.parse(parts[1]));
  }

  static bool isFlippable(Instruction instruction) {
    return instruction.name == "jmp" || instruction.name == "nop";
  }

  void flip() {
    if (name == "jmp") {
      name = "nop";
    } else if (name == "nop") {
      name = "jmp";
    }
  }
}
