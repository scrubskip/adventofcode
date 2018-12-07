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

        self.assertEquals(1, len(roots))
        self.assertEquals('B', roots[0].label)
        self.assertFalse(roots[0].has_prereqs())

    def test_sequence(self):
        nodeA = Node('A')
        nodeB = Node('B')
        nodeA.add_prereq('B')
        nodeC = Node('C')
        nodeC.add_prereq('B')
        nodes = {'A' : nodeA, 'B' : nodeB, 'C' : nodeC}

        self.assertEquals('BAC', steps.get_sequence(nodes))


    def test_remove_prereqs(self):
        nodeA = Node('A')
        nodeB = Node('B')
        nodeA.add_prereq('B')

        roots = steps.find_roots({'A': nodeA, 'B': nodeB})
        self.assertTrue(nodeA.has_prereq('B'))
        self.assertEquals(1, len(roots))
        nodeA.remove_prereq('B')
        self.assertFalse(nodeA.has_prereq('B'))

        roots = steps.find_roots({'A': nodeA, 'B': nodeB})
        self.assertEquals(2, len(roots))

if __name__ == '__main__':
    unittest.main()
