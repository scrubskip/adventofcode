import unittest
from carts import Cart, Track


class CartTest(unittest.TestCase):
    def test_parse(self):
        input = [
            "|",
            "v",
            "|",
            "|",
            "^",
            "|"
        ]

        track = Track.parse(input)
        self.assertEqual(2, len(track.carts))
        self.assertEqual((0, 1), track.carts[0].position)
        self.assertEqual("v", track.carts[0].direction)
        self.assertEqual((0, 4), track.carts[1].position)
        self.assertEqual("^", track.carts[1].direction)

    def test_tick(self):
        input = [
            "|",
            "v",
            "|",
            "|",
            "^",
            "|"
        ]

        track = Track.parse(input)
        track.do_tick()
        self.assertEqual((0, 2), track.carts[0].position)
        self.assertEqual("v", track.carts[0].direction)
        self.assertEqual((0, 3), track.carts[1].position)
        self.assertEqual("^", track.carts[1].direction)

    def test_aoc_input(self):
        input = [
            "/->-\\        ",
            "|   |  /----\\",
            "| /-+--+-\\  |",
            "| | |  | v  |",
            "\\-+-/  \\-+--/",
            "  \\------/   "
        ]
        track = Track.parse(input)
        self.assertEqual(2, len(track.carts))
        self.assertEqual((2, 0), track.carts[0].position)
        self.assertEqual(">", track.carts[0].direction)
        self.assertEqual((9, 3), track.carts[1].position)
        self.assertEqual("v", track.carts[1].direction)
        track.do_tick()
        self.assertEqual(">", track.carts[1].direction)
        track.do_tick()
        self.assertEqual("v", track.carts[0].direction)
        while track.do_tick():
            pass

    def test_aoc_remove_input(self):
        input = [
            "/>-<\\  ",
            "|   |  ",
            "| /<+-\\",
            "| | | v",
            "\\>+</ |",
            "  |   ^",
            "  \\<->/"
        ]
        track = Track.parse(input)
        self.assertEqual(9, len(track.carts))
        while track.do_tick_remove():
            pass
        print "Remaining ", track.carts


if __name__ == "__main__":
    unittest.main()
