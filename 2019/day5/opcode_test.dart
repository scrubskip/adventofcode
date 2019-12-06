import "package:test/test.dart";
import 'opcode.dart';

void main() {
  test("Input simple", _testSimple);
  test("Input / output", _testInputOutput);
  test("Compare", _testCompare);
  test("Jump", _testJump);
  test("AoC", _testAoc);
}

void _testSimple() {
  List<int> inputData = [1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50];
  List<int> outputData = [3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50];
  expect(outputData, run(inputData));
}

void _testInputOutput() {
  List<int> inputData = [3, 0, 4, 0, 99];
  List<int> output = run(inputData, 1);
  expect(output[0], 1);
}

void _testCompare() {
  List<int> input1 = [3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8];
  List<int> input2 = [3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8];
  List<int> inputImmediate1 = [3, 3, 1108, -1, 8, 3, 4, 3, 99];
  List<int> inputImmediate2 = [3, 3, 1107, -1, 8, 3, 4, 3, 99];

  expect(run(input1, 8, true), [1]);
  expect(run(input1, 7, true), [0]);
  expect(run(inputImmediate1, 8, true), [1]);
  expect(run(inputImmediate1, 7, true), [0]);

  expect(run(input2, 8, true), [0]);
  expect(run(input2, 7, true), [1]);
  expect(run(inputImmediate2, 8, true), [0]);
  expect(run(inputImmediate2, 7, true), [1]);
}

void _testJump() {
  List<int> input1 = [3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9];
  List<int> inputImmediate1 = [3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1];

  expect(run(input1, 0, true), [0]);
  expect(run(input1, 23, true), [1]);

  expect(run(inputImmediate1, 0, true), [0]);
  expect(run(inputImmediate1, -12, true), [1]);
}

void _testAoc() {
  List<int> input = [
    3,
    21,
    1008,
    21,
    8,
    20,
    1005,
    20,
    22,
    107,
    8,
    21,
    20,
    1006,
    20,
    31,
    1106,
    0,
    36,
    98,
    0,
    0,
    1002,
    21,
    125,
    20,
    4,
    20,
    1105,
    1,
    46,
    104,
    999,
    1105,
    1,
    46,
    1101,
    1000,
    1,
    20,
    4,
    20,
    1105,
    1,
    46,
    98,
    99
  ];

  expect(run(input, 7, true), [999]);
  expect(run(input, 8, true), [1000]);
  expect(run(input, 9, true), [1001]);
}
