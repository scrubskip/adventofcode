import unittest
import power


class PowerTestCase(unittest.TestCase):
    def test_hundreds_digit(self):
        self.assertEquals(0, power.get_hundreds_digit(3))
        self.assertEquals(3, power.get_hundreds_digit(300))

    def test_power_level(self):
        self.assertEqual(4, power.get_power(3, 5, 8))
        self.assertEqual(-5, power.get_power(122, 79, 57))
        # Fuel cell at 217,196, grid serial number 39: power level  0.
        self.assertEqual(0, power.get_power(217, 196, 39))
        # Fuel cell at 101,153, grid serial number 71: power level  4.
        self.assertEqual(4, power.get_power(101, 153, 71))

    def test_power_matrix(self):
        matrix = power.create_power_matrix(18)
        self.assertEquals(4, matrix[32][44])
        self.assertEquals(4, matrix[33][44])
        self.assertEquals(4, matrix[34][44])
        self.assertEquals(3, matrix[32][45])
        self.assertEquals(3, matrix[33][45])
        self.assertEquals(4, matrix[34][45])
        self.assertEquals(1, matrix[32][46])
        self.assertEquals(2, matrix[33][46])
        self.assertEquals(4, matrix[34][46])

    def test_sum_matrix(self):
        matrix = power.create_power_matrix(18)
        sum_matrix = power.create_sum_matrix(matrix)
        self.assertEquals(29, sum_matrix[32][44])
        max_value, max_x, max_y = power.get_max(sum_matrix)
        self.assertEqual(29, max_value)
        self.assertEqual(32, max_x)
        self.assertEqual(44, max_y)

    def test_list_sum_matrix_aoc(self):
        matrix = power.create_power_matrix(18)
        list_sum_matrix = power.create_list_sum_matrix(matrix, 20)
        self.assertEquals(29, list_sum_matrix[32][44][2])
        max_value, max_x, max_y, max_square_size = power.get_list_max(
            list_sum_matrix)
        self.assertEqual(113, max_value)
        self.assertEqual(89, max_x)
        self.assertEqual(268, max_y)
        self.assertEqual(16, max_square_size)


if __name__ == '__main__':
    unittest.main()
