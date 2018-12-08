import largest_area
from largest_area import Point
import unittest


class LargestAreaTestCase(unittest.TestCase):
    def test_visible_range(self):
        points = [
            Point(5, 0),
            Point(10, 1),
            Point(25, 10)
        ]

        vrange = largest_area.get_visible_range(points)
        self.assertEquals(5, vrange[0].x)
        self.assertEquals(0, vrange[0].y)
        self.assertEquals(25, vrange[1].x)
        self.assertEquals(10, vrange[1].y)


if __name__ == '__main__':
    unittest.main()
