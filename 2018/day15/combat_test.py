import unittest
import combat
from combat import Board, Unit


class CombatTest(unittest.TestCase):
    def test_parse(self):
        lines = [
            "#######",
            "#.G.E.#",
            "#E.G.E#",
            "#.G.E.#",
            "#######"
        ]
        board = Board.parse(lines)
        self.assertEqual(7, board.width)
        self.assertEqual(5, board.height)
        self.assertEqual(7, len(board.units))
        # print board.units

    def test_unit_pieces(self):
        lines = [
            "#######",
            "#E..G.#",
            "#...#.#",
            "#.G.#G#",
            "#######"
        ]
        board = Board.parse(lines)
        self.assertEqual(7, board.width)
        self.assertEqual(5, board.height)
        self.assertEqual(4, len(board.units))
        elf = board.units[0]
        targets = elf.get_targets(board)
        self.assertEqual(3, len(targets))
        positions = elf.get_adjacent_positions()
        # print positions
        self.assertListEqual([(1, 0), (0, 1), (2, 1), (1, 2)],
                             positions)
        move_spaces = elf.get_possible_move_spaces(targets, board)
        # print move_spaces
        self.assertListEqual(
            [(3, 1), (5, 1), (2, 2), (5, 2), (1, 3), (3, 3)], move_spaces)


if __name__ == '__main__':
    unittest.main()
