import unittest
import cave
from cave import Scanner

class CaveTest(unittest.TestCase):
    def test_calculations(self):
        scanner = Scanner(10, 10, 510)
        self.assertEqual(0, scanner.get_geologic_index(0, 0))
        self.assertEqual(510, scanner.get_erosion_level(0, 0))
        self.assertEqual(0, scanner.get_risk_level(0, 0 ))

        self.assertEqual(16807, scanner.get_geologic_index(1, 0))
        self.assertEqual(17317, scanner.get_erosion_level(1, 0))
        self.assertEqual(1, scanner.get_risk_level(1, 0))

        self.assertEqual(48271, scanner.get_geologic_index(0, 1))
        self.assertEqual(8415, scanner.get_erosion_level(0, 1))
        self.assertEqual(0, scanner.get_risk_level(0, 1))

    def test_risk_level(self):
        scanner = Scanner(10, 10, 510)
        self.assertEqual(114, scanner.get_risk_level_area(0, 0, 10, 10))

        

if __name__ == '__main__':
    unittest.main()
