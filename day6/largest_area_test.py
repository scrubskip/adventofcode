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
        margin = largest_area.get_margin()
        self.assertEquals(5 - margin, vrange[0].x)
        self.assertEquals(0 - margin, vrange[0].y)
        self.assertEquals(25 + margin, vrange[1].x)
        self.assertEquals(10 + margin, vrange[1].y)

    def test_tiny_matrix(self):
        points = [
            Point(1, 1),
            Point(1, 6),
            Point(8, 3),
            Point(3, 4),
            Point(5, 5),
            Point(8, 9)
        ]
        vrange = largest_area.get_visible_range(points)
        dmatrix = largest_area.make_matrix(points, vrange)

        # print dmatrix
        largest = largest_area.find_largest_internal(dmatrix, points)
        self.assertEquals(17, largest)

    def test_calculate_distance(self):
        self.assertEquals(2, largest_area.calculate_distance(
            Point(1, 1), Point(2, 2)))
        self.assertEquals(6, largest_area.calculate_distance(
            Point(0, 1), Point(2, 5)))
        self.assertEquals(3, largest_area.calculate_distance(
            Point(0, 4), Point(1, 6)))

    def tast_make_matrix(self):
        points = [
            Point(5, 1),
            Point(10, 2),
            Point(2, 21),
            Point(3, 7),
            Point(25, 10)
        ]
        vrange = largest_area.get_visible_range(points)
        dmatrix = largest_area.make_matrix(points, vrange)
        margin = largest_area.get_margin()
        self.assertEquals(23 + margin * 2, len(dmatrix))
        self.assertEquals(20 + margin * 2, len(dmatrix[0]))

    def test_area(self):
        points = [
            Point(0, 0),
            Point(1, 2),
            Point(10, 0),
            Point(10, 10),
            Point(0, 10),
            Point(5, 5),
            Point(7, 7)
        ]
        vrange = largest_area.get_visible_range(points)
        dmatrix = largest_area.make_matrix(points, vrange)
        largest = largest_area.find_largest_internal(dmatrix, points)
        #print dmatrix
        self.assertEquals(19, largest)

    def test_total_distance_matrix(self):
        points = [
            Point(1, 1),
            Point(1, 6),
            Point(8, 3),
            Point(3, 4),
            Point(5, 5),
            Point(8, 9)
        ]
        vrange = largest_area.get_visible_range(points)
        dmatrix = largest_area.make_total_distance_matrix(points, vrange)
        #print dmatrix
        self.assertEquals(
            16, largest_area.find_region_within_range(dmatrix, 32))


if __name__ == '__main__':
    unittest.main()
