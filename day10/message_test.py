import message
import unittest
from message import Point


class MessageTestCase(unittest.TestCase):
    def test_parse(self):
        point_str = "position=<-3,  6> velocity=< 2, -1>"
        point = Point.parse(point_str)


if __name__ == '__main__':
    unittest.main()
