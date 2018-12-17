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
        print board.units


if __name__ == '__main__':
    unittest.main()
