import "package:test/test.dart";
import 'opcode.dart';

void main() {
  test("Input simple", _testSimple);
}

void _testSimple() {
  List<int> inputData = [1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50];
  List<int> outputData = [3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50];
  expect(outputData, run(inputData));
}
