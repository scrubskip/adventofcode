import steps
from steps import Node
import unittest


class StepsTestCase(unittest.TestCase):
    def test_parse_simple(self):
        nodes = steps.parse_lines(
            ["Step A must be finished before step B can begin."])
        self.assertTrue(nodes is not None)
        self.assertTrue(nodes['A'] is not None)
        self.assertTrue(nodes['B'] is not None)
        self.assertTrue(nodes['A'].has_prereq('B'))

    def test_node(self):
        nodeA = Node('A')
        nodeB = Node('B')
        nodeA.add_prereq('B')

        self.assertTrue(nodeA.has_prereqs())
        self.assertFalse(nodeB.has_prereqs())

    def test_find_roots(self):
        nodeA = Node('A')
        nodeB = Node('B')
        nodeA.add_prereq('B')

        roots = steps.find_roots({'A': nodeA, 'B': nodeB})
        print roots
        self.assertEquals(1, len(roots))


if __name__ == '__main__':
    unittest.main()
