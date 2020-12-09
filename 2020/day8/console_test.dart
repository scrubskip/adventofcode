import "package:test/test.dart";

import "console.dart";

void main() {
  test("aoc input", _testAocInput);
}

void _testAocInput() {
  var data = [
    "nop +0",
    "acc +1",
    "jmp +4",
    "acc +3",
    "jmp -3",
    "acc -99",
    "acc +1",
    "jmp -4",
    "acc +6",
  ];
  List<Instruction> input = data.map(Instruction.parse).toList();
  expect(findLoop(input), 5);
}
