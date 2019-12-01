import unittest
import recipe
from recipe import Board, SequenceFinder

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
        
    def test_find_str(self):
        board = Board()
        index = board.find_string('51589')
        self.assertEqual(9, index)
        board = Board()
        self.assertEqual(5, board.find_string('01245'))
        board = Board()
        self.assertEqual(2018, board.find_string('59414'))

    def test_sequence_finder(self):
        finder = SequenceFinder("test")
        self.assertEquals((False, 1), finder.input('t'))
        self.assertEquals((False, 0), finder.input('q'))
        self.assertEquals((False, 1), finder.input('t'))
        self.assertEquals((False, 1), finder.input('t'))
        self.assertEquals((False, 2), finder.input('e'))
        self.assertEquals((False, 3), finder.input('s'))
        self.assertEquals((True, 4), finder.input('t'))


if __name__ == "__main__":
    unittest.main()
