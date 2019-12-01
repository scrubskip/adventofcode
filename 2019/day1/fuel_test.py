import fuel
import unittest


class FuelTestCase(unittest.TestCase):
    def test_simple(self):
        self.assertEqual(2, fuel.get_fuel_requirement(12))
        self.assertEqual(2, fuel.get_fuel_requirement(14))
        self.assertEqual(654, fuel.get_fuel_requirement(1969))
        self.assertEqual(33583, fuel.get_fuel_requirement(100756))


if __name__ == '__main__':
    unittest.main()
