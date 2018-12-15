import unittest
import recipe
from recipe import Board

class RecipeTest(unittest.TestCase):
    def test_simple(self):
        board = Board()
        num_recipes = board.do_tick()
        self.assertEqual(4, num_recipes)
        print board
        self.assertEqual(0, board.elf1_index)
        self.assertEqual(1, board.elf2_index)
        num_recipes = board.do_tick()
        self.assertEqual(6, num_recipes)
        self.assertEqual(4, board.elf1_index)
        self.assertEqual(3, board.elf2_index)

    def test_aoc_input(self):
        board = Board()
        self.assertEqual('5158916779', board.get_ten_after(9))
        board = Board()
        self.assertEqual('0124515891', board.get_ten_after(5))
        board = Board()
        self.assertEqual('9251071085', board.get_ten_after(18))
        


if __name__ == "__main__":
    unittest.main()
