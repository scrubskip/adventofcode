import "package:test/test.dart";
import "dart:io";
import "opcode.dart";

void main() {
  test("Board", _testGameBoard);
}

void _testGameBoard() {
  GameBoard board = new GameBoard();
  [1, 2, 3, 4, 5, 6].forEach(board.handleOutput);
  expect(board.getItem(Point(1, 2)), 3);
  expect(board.getItem(Point(4, 5)), 6);
  expect(board.getTileCount(2), 0);
  expect(board.getTileCount(3), 1);
  expect(board.getTileCount(6), 1);
}
