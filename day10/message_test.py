import message
import unittest
from message import Point


class MessageTestCase(unittest.TestCase):
    def test_parse(self):
        point_str = "position=<-3,  6> velocity=< 2, -1>"
        point = Point.parse(point_str)
        self.assertEqual(-3, point.x)
        self.assertEqual(6, point.y)
        self.assertEqual(2, point.dx)
        self.assertEqual(-1, point.dy)

    def test_visible_range(self):
        points = [
            Point(2, -2, 0, 0),
            Point(5, 5, 0, 0),
            Point(0, 10, 0, 0)
        ]

        vrange = message.get_visible_range(points)
        self.assertEquals(0, vrange[0].x)

    def test_aoc_input(self):
        point_strs = [
            "position=< 9,  1> velocity=< 0,  2>",
            "position=< 7,  0> velocity=<-1,  0>",
            "position=< 3, -2> velocity=<-1,  1>",
            "position=< 6, 10> velocity=<-2, -1>",
            "position=< 2, -4> velocity=< 2,  2>",
            "position=<-6, 10> velocity=< 2, -2>",
            "position=< 1,  8> velocity=< 1, -1>",
            "position=< 1,  7> velocity=< 1,  0>",
            "position=<-3, 11> velocity=< 1, -2>",
            "position=< 7,  6> velocity=<-1, -1>",
            "position=<-2,  3> velocity=< 1,  0>",
            "position=<-4,  3> velocity=< 2,  0>",
            "position=<10, -3> velocity=<-1,  1>",
            "position=< 5, 11> velocity=< 1, -2>",
            "position=< 4,  7> velocity=< 0, -1>",
            "position=< 8, -2> velocity=< 0,  1>",
            "position=<15,  0> velocity=<-2,  0>",
            "position=< 1,  6> velocity=< 1,  0>",
            "position=< 8,  9> velocity=< 0, -1>",
            "position=< 3,  3> velocity=<-1,  1>",
            "position=< 0,  5> velocity=< 0, -1>",
            "position=<-2,  2> velocity=< 2,  0>",
            "position=< 5, -2> velocity=< 1,  2>",
            "position=< 1,  4> velocity=< 2,  1>",
            "position=<-2,  7> velocity=< 2, -2>",
            "position=< 3,  6> velocity=<-1, -1>",
            "position=< 5,  0> velocity=< 1,  0>",
            "position=<-6,  0> velocity=< 2,  0>",
            "position=< 5,  9> velocity=< 1, -2>",
            "position=<14,  7> velocity=<-2,  0>",
            "position=<-3,  6> velocity=< 2, -1>"
        ]

        points = map(Point.parse, point_strs)
        self.assertEqual(len(points), len(filter(None, points)))
        
        matrix, found_visible = message.get_points_at_time(points, 3)
        #message.print_matrix(matrix)
        #print found_visible
        message.guess_time(points, 0)

    def test_time(self):
        point_strs = [
            "position=<-31684, -53051> velocity=< 3,  5>",
            "position=< 10860,  32026> velocity=<-1, -3>"
        ]
        points = map(Point.parse, point_strs)
        #matrix, found_visible = message.get_points_at_time(points, 10768)
        print message.get_visible_range(points)


if __name__ == '__main__':
    unittest.main()
