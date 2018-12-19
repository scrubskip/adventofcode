import unittest
import water
from water import Ground


class WaterTest(unittest.TestCase):
    def test_parse(self):
        ground = Ground()
        ground.add_input("x=495, y=2..7")
        for i in range(2, 8):
            self.assertTrue(ground.is_clay((495, i)))
        self.assertEqual(2, ground.min_y)
        self.assertEqual(7, ground.max_y)
        self.assertFalse(ground.is_clay((495, 9)))
        # test y as the fixed value
        ground.add_input("y=7, x=495..501")
        for i in range(495, 501):
            self.assertTrue(ground.is_clay((i, 7)))
        self.assertEqual(2, ground.min_y)
        self.assertEqual(7, ground.max_y)

    def test_aoc(self):
        lines = [
            "x=495, y=2..7",
            "y=7, x=495..501",
            "x=501, y=3..7",
            "x=498, y=2..4",
            "x=506, y=1..2",
            "x=498, y=10..13",
            "x=504, y=10..13",
            "y=13, x=498..504"
        ]
        ground = Ground()
        map(ground.add_input, lines)
        self.assertEqual(1, ground.min_y)
        self.assertEqual(13, ground.max_y)

        ground.emit_water()
        ground.print_board()
        print len(ground.reachable)


if __name__ == "__main__":
    unittest.main()
