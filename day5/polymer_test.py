import polymer
import unittest


class PolymerTestCase(unittest.TestCase):

    def test_simple(self):
        self.assertEquals(("abc", False), polymer.reduce_polymer("abc"))
        self.assertEquals(("bc", True), polymer.reduce_polymer("Aabc"))
        self.assertEquals(("bc", True), polymer.reduce_polymer("aAbc"))

    def test_recursive(self):
        self.assertEquals("c", polymer.reduce_polymer_recursive("BaAbc"))


if __name__ == '__main__':
    unittest.main()
