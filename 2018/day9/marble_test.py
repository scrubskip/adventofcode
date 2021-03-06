import unittest
import marble
from marble import Marble, MarbleGame, MarbleGame2


class MarbleTestCase(unittest.TestCase):
    def test_simple(self):
        game = MarbleGame(9, 25)
        game.run_game()
        self.assertEquals(7, game.current_player)
        board_state = [0, 16, 8, 17, 4, 18, 19, 2, 24, 20,
                       25, 10, 21, 5, 22, 11, 1, 12, 6, 13, 3, 14, 7, 15]
        self.assertEquals(board_state, game.board)
        self.assertEqual(32, game.get_high_score())
        print "array type", game.player_scores

    def test_multiple(self):
        game = MarbleGame(10, 1618)
        game.run_game()
        self.assertEqual(8317, game.get_high_score())

    def test_print_node(self):
        marble = Marble(0)
        marble.add_next(marble)
        marble1 = Marble(1)
        marble.add_next(marble1)

        print marble.print_marble()
        print marble1.print_marble()

    def test_deque(self):
        game = MarbleGame2(9, 25)
        game.run_game()
        print "node type", game.player_scores
        self.assertEquals(7, game.current_player)
        self.assertEqual(32, game.get_high_score())

        game = MarbleGame2(10, 1618)
        game.run_game()
        self.assertEqual(8317, game.get_high_score())


if __name__ == '__main__':
    unittest.main()
