import unittest
import lumber
from lumber import Board


class LumberTest(unittest.TestCase):
    def test_aoc(self):
        lines = [
            ".#.#...|#.",
            ".....#|##|",
            ".|..|...#.",
            "..|#.....#",
            "#.#|||#|#|",
            "...#.||...",
            ".|....|...",
            "||...#|.#|",
            "|.||||..|.",
            "...#.|..|."
        ]
        board = Board()
        map(board.add_row, lines)
        self.assertEqual(10, board.width)
        self.assertEqual(10, board.height)
        self.assertEqual(lines, board.state)
        board.print_board()

        new_lines = [
            ".......##.",
            "......|###",
            ".|..|...#.",
            "..|#||...#",
            "..##||.|#|",
            "...#||||..",
            "||...|||..",
            "|||||.||.|",
            "||||||||||",
            "....||..|."
        ]
        board.do_tick()
        self.assertEqual(new_lines, board.state)

        new_lines = [
            ".......#..",
            "......|#..",
            ".|.|||....",
            "..##|||..#",
            "..###|||#|",
            "...#|||||.",
            "|||||||||.",
            "||||||||||",
            "||||||||||",
            ".|||||||||"
        ]
        board.do_tick()
        self.assertEqual(new_lines, board.state)

        new_lines = [
            ".||##.....",
            "||###.....",
            "||##......",
            "|##.....##",
            "|##.....##",
            "|##....##|",
            "||##.####|",
            "||#####|||",
            "||||#|||||",
            "||||||||||"
        ]

        for _ in range(8):
            board.do_tick()
        self.assertEqual(new_lines, board.state)

        self.assertEqual(1147, board.get_resource_value())


if __name__ == '__main__':
    unittest.main()
