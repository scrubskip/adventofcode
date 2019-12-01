import unittest
import plants
from plants import PlantArray, PlantRule


class PlantsTestCase(unittest.TestCase):
    def test_parse_rule(self):
        rule = PlantRule.parse("...## => #")
        self.assertEqual("...##", rule.pattern)
        self.assertEqual("#", rule.end_state)

    def test_rule_changes(self):
        rule = PlantRule.parse("...## => #")

        changes = rule.get_changes("...##...##")
        self.assertTupleEqual((2, '#'), changes[0])
        self.assertTupleEqual((7, '#'), changes[1])

        rule = PlantRule.parse(".#### => .")

        changes = rule.get_changes("...####.####.#.#")
        self.assertTupleEqual((4, '.'), changes[0])
        self.assertTupleEqual((9, '.'), changes[1])

    def test_ending_rule(self):
        rules = map(PlantRule.parse, [
            "##### => #",
            "####. => #",
            "###.. => #"
        ])
        plants = PlantArray("....#######")
        plants.apply_rules(rules)
        print "test ", plants.state


    def test_rule_apply(self):
        rule = PlantRule.parse("...## => #")
        plants = PlantArray("...##...##")
        plants.apply_rules([rule])
        self.assertEquals("......#....#......", plants.state)
        self.assertEquals(4, plants.zero_index)

    def test_plant_num(self):
        plants = PlantArray("...##...##")
        self.assertEquals(0, plants.zero_index)
        self.assertEqual(3 + 4 + 8 + 9, plants.get_plant_num())
        plants.apply_rules([])
        self.assertEquals(4, plants.zero_index)
        self.assertEqual(0, plants.get_plant_num())

    def tast_multirule_aoc(self):
        rules = map(PlantRule.parse, [
            "...## => #",
            "..#.. => #",
            ".#... => #",
            ".#.#. => #",
            ".#.## => #",
            ".##.. => #",
            ".#### => #",
            "#.#.# => #",
            "#.### => #",
            "##.#. => #",
            "##.## => #",
            "###.. => #",
            "###.# => #",
            "####. => #"
        ])
        plants = PlantArray("#..#.#..##......###...###")
        plants.apply_rules(rules)
        print plants
        print plants.get_plant_num()
        self.assertEquals("...#...#....#.....#..#..#..#....",
                          plants.get_state_with_left_padding(3))
        self.assertEquals(4, plants.zero_index)
        plants.apply_rules(rules)
        print plants.get_state_with_left_padding(3)
        self.assertEquals("...##..##...##....#..#..#..##...",
                          plants.get_state_with_left_padding(3))
        plants.apply_rules(rules)
        print plants.get_state_with_left_padding(3)
        self.assertEquals(
            "..#.#...#..#.#....#..#..#...#.......",
            plants.get_state_with_left_padding(3))
        for _ in range(17):
            plants.apply_rules(rules)
            print plants.get_state_with_left_padding(3)

        #print plants.get_plant_num()
        self.assertEqual(325, plants.get_plant_num())

if __name__ == '__main__':
    unittest.main()
