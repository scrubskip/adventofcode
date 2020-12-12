import 'dart:convert';
import 'dart:io';

import "package:args/args.dart";

void main(List<String> args) {
  final parser = ArgParser();
  ArgResults argResults = parser.parse(args);
  List<String> inputStr = new File(argResults.rest[0]).readAsLinesSync();
  List<int> input = inputStr.map(int.parse).toList();
  stdout.writeln(
      findSmallLargeInContiguousRange(input, findFirstInvalid(input, 25)));
}

int findFirstInvalid(List<int> input, int windowSize) {
  // First 25 are input
  int windowIndex = 0;
  int startIndex = windowSize;

  while (startIndex < input.length) {
    int searchNumber = input[startIndex];
    if (getTwoNumbers(
            input.sublist(windowIndex, startIndex), input[startIndex]) ==
        -1) {
      return searchNumber;
    }
    startIndex++;
    windowIndex++;
  }
  return -1;
}

int findSmallLargeInContiguousRange(List<int> input, int sum) {
  int currentSum = input[0];
  int startIndex = 0;
  int endIndex = 0;

  while (startIndex < input.length && endIndex < input.length) {
    if (currentSum < sum) {
      endIndex++;
      currentSum += input[endIndex];
    } else if (currentSum > sum) {
      currentSum -= input[startIndex];
      startIndex++;
    } else {
      // We are equal, this is great, now find the smallest and largest in the range.
      return findSmallestLargestInRange(input, startIndex, endIndex)
          .reduce((previous, element) => previous + element);
    }
  }
  return -1;
}

List<int> findSmallestLargestInRange(
    List<int> input, int startIndex, int endIndex) {
  int min = input[startIndex];
  int max = input[startIndex];
  input.getRange(startIndex, endIndex + 1).forEach((element) {
    if (element < min) {
      min = element;
    }
    if (element > max) {
      max = element;
    }
  });
  return [min, max];
}

int getTwoNumbers(List<int> input, int searchValue) {
  for (int index = 0; index < input.length; index++) {
    for (int indexOther = index + 1; indexOther < input.length; indexOther++) {
      if (input[index] + input[indexOther] == searchValue) {
        return input[index] * input[indexOther];
      }
    }
  }
  return -1;
}
