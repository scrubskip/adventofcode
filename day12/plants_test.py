import unittest
import plants
from plants import PlantRule


class PlantsTestCase(unittest.TestCase):
    def test_parse_rule(self):
        rule = PlantRule.parse("...## => #")
        self.assertEqual("...##", rule.pattern)
        self.assertEqual("#", rule.end_state)

    def test_rule_apply(self):
        rule = PlantRule.parse("...## => #")

        changes = rule.get_changes("...##...##")
        self.assertTupleEqual((2, '#'), changes[0])
        self.assertTupleEqual((7, '#'), changes[1])


if __name__ == '__main__':
    unittest.main()
