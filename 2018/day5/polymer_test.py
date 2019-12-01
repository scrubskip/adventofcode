import polymer
import unittest


class PolymerTestCase(unittest.TestCase):

    def test_simple(self):
        self.assertEquals(("abc", False), polymer.reduce_polymer("abc"))
        self.assertEquals(("bc", True), polymer.reduce_polymer("Aabc"))
        self.assertEquals(("bc", True), polymer.reduce_polymer("aAbc"))

    def test_recursive(self):
        self.assertEquals("c", polymer.reduce_polymer_recursive("BaAbc"))

    def test_remove(self):
        self.assertEquals("c", polymer.remove_and_reduce("Aac", "a"))
        self.assertEquals("bc", polymer.remove_and_reduce("bAac", "a"))
        self.assertEquals("c", polymer.remove_and_reduce("bAaBc", "a"))


if __name__ == '__main__':
    unittest.main()
