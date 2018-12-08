import steps
from steps import Node, Worker
import unittest


class StepsTestCase(unittest.TestCase):
    def test_parse_simple(self):
        nodes = steps.parse_lines(
            ["Step A must be finished before step B can begin."])
        self.assertTrue(nodes is not None)
        self.assertTrue(nodes['A'] is not None)
        self.assertTrue(nodes['B'] is not None)
        self.assertTrue(nodes['B'].has_prereq('A'))

    def test_node(self):
        nodeA = Node('A')
        nodeB = Node('B')
        nodeA.add_prereq('B')

        self.assertTrue(nodeA.has_prereqs())
        self.assertFalse(nodeB.has_prereqs())

    def test_find_root(self):
        nodeA = Node('A')
        nodeB = Node('B')
        nodeA.add_prereq('B')

        root = steps.find_root({'A': nodeA, 'B': nodeB})

        self.assertEquals('B', root.label)
        self.assertFalse(root.has_prereqs())

    def test_sequence(self):
        nodeA = Node('A')
        nodeB = Node('B')
        nodeA.add_prereq('B')
        nodeC = Node('C')
        nodeC.add_prereq('B')
        nodes = {'A' : nodeA, 'B' : nodeB, 'C' : nodeC}

        #self.assertEquals('BAC', steps.get_sequence(nodes))

        nodeD = Node('D')
        nodeD.add_prereq('A')
        nodes = {'A' : nodeA, 'B' : nodeB, 'C' : nodeC, 'D' : nodeD}
        self.assertEquals('BACD', steps.get_sequence(nodes))


    def test_remove_prereqs(self):
        nodeA = Node('A')
        nodeB = Node('B')
        nodeA.add_prereq('B')

        root = steps.find_root({'A': nodeA, 'B': nodeB})
        self.assertTrue(nodeA.has_prereq('B'))
        self.assertEquals('B', root.label)
        nodeA.remove_prereq('B')
        self.assertFalse(nodeA.has_prereq('B'))

        root = steps.find_root({'A': nodeA, 'B': nodeB})
        self.assertEquals('A', root.label)

    def test_sample_input(self):
        test_data = ["Step C must be finished before step A can begin.",
            "Step C must be finished before step F can begin.",
            "Step A must be finished before step B can begin.",
            "Step A must be finished before step D can begin.",
            "Step B must be finished before step E can begin.",
            "Step D must be finished before step E can begin.",
            "Step F must be finished before step E can begin."]
        nodes = steps.parse_lines(test_data)
        self.assertEquals("CABDFE", steps.get_sequence(nodes))

    def test_worker(self):
        workerA = Worker()
        self.assertEquals(0, workerA.current_time)
        workerA.start_job('A')
        self.assertEquals(0, workerA.start_time)
        self.assertEquals(61, workerA.end_time)

        workerA.do_work()
        self.assertEquals(1, workerA.current_time)
        for i in range(60):
            self.assertTrue(workerA.is_working(), "Should still be working")
            workerA.do_work()

        self.assertEquals(61, workerA.current_time)
        self.assertFalse(workerA.is_working(), 'Should not be working')
        self.assertEquals('A', workerA.completed_job)

if __name__ == '__main__':
    unittest.main()
