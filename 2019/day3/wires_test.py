import unittest
import wires


class WiresTestCase(unittest.TestCase):
    def test_points(self):
        coordinates_1 = wires.get_wire_coordinates(["U7", "R6", "D4", "L4"])
        output = map(make_tuple,
                     [[0, 1], [0, 2], [0, 3], [0, 4], [0, 5], [2, 3], [0, 6],
                      [3, 3], [0, 7], [4, 3], [1, 7], [5, 3], [2, 7], [6, 3],
                      [3, 7], [6, 4], [4, 7], [6, 5], [5, 7], [6, 6], [6, 7]])
        self.assertListEqual(output, coordinates_1)
        coordinates_2 = wires.get_wire_coordinates(["R8", "U5", "L5", "D3"])
        output = map(make_tuple,
                     [[1, 0], [2, 0], [3, 0], [4, 0], [5, 0], [3, 2], [6, 0],
                      [3, 3], [7, 0], [3, 4], [8, 0], [3, 5], [8, 1], [4, 5],
                      [8, 2], [5, 5], [8, 3], [6, 5], [8, 4], [7, 5], [8, 5]])

        self.assertListEqual(output, coordinates_2)

        intersection = wires.get_intersection(coordinates_1, coordinates_2)
        self.assertEqual(6, wires.get_distance(intersection))

    def test_points_complex(self):
        coordinates_1 = wires.get_wire_coordinates(
            "R75,D30,R83,U83,L12,D49,R71,U7,L72".split(","))
        coordinates_2 = wires.get_wire_coordinates(
            "U62,R66,U55,R34,D71,R55,D58,R83".split(","))
        intersection = wires.get_intersection(coordinates_1, coordinates_2)
        self.assertEqual(159, wires.get_distance(intersection))

        coordinates_1 = wires.get_wire_coordinates(
            "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51".split(","))
        coordinates_2 = wires.get_wire_coordinates(
            "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7".split(","))
        intersection = wires.get_intersection(coordinates_1, coordinates_2)
        self.assertEqual(135, wires.get_distance(intersection))


def make_tuple(point):
    return tuple(point)


if __name__ == '__main__':
    unittest.main()
