import "package:test/test.dart";
import "opcode.dart";
import "dart:io";

void main() {
  test("Copy", _testCopy);
  test("output", _testOutput);
  test("big number", _testBigNumber);
  test("real", _testReal);
}

void _testCopy() {
  List<int> input = [
    109,
    1,
    204,
    -1,
    1001,
    100,
    1,
    100,
    1008,
    100,
    16,
    101,
    1006,
    101,
    0,
    99
  ];
  List<int> outputList = [];
  List<int> data = run(input, null, (output) => outputList.add(output));
  expect(input, outputList);
}

void _testOutput() {
  List<int> input = [104, 1125899906842624, 99];
  List<int> outputList = [];
  List<int> data = run(input, null, (output) => outputList.add(output));
  expect(outputList, [1125899906842624]);
}

void _testBigNumber() {
  List<int> input = [1102, 34915192, 34915192, 7, 4, 7, 99, 0];
  List<int> outputList = [];
  List<int> data = run(input, null, (output) => outputList.add(output));
  expect(outputList, [1219070632396864]);
}

void _testReal() {
  String input = new File("2019/day9/day9input.txt").readAsStringSync();
  run(input.split(",").map(int.parse).toList(), 2,
      (output) => stdout.writeln("$output"));
}
