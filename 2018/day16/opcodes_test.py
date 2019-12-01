import unittest
import opcodes
from opcodes import OpcodeTestCase, OpcodeExecutor


class OpcodeTests(unittest.TestCase):
    def test_parse(self):
        test = OpcodeTestCase.parse(
            "Before: [1, 0, 2, 0]", "4 1 0 1", "After:  [1, 1, 2, 0]")
        self.assertIsNotNone(test)
        self.assertEqual([1, 0, 2, 0], test.initial)
        self.assertEqual([4, 1, 0, 1], test.command)
        self.assertEqual([1, 1, 2, 0], test.result)

    def test_op(self):
        executor = OpcodeExecutor()
        executor.executeCommand("mulr", [2, 1, 2], initial=[3, 2, 1, 1])
        self.assertEqual([3, 2, 2, 1], executor.registers)
        executor.executeCommand("addi", [2, 1, 2], initial=[3, 2, 1, 1])
        self.assertEqual([3, 2, 2, 1], executor.registers)
        executor.executeCommand("seti", [2, 1, 2], initial=[3, 2, 1, 1])
        self.assertEqual([3, 2, 2, 1], executor.registers)

    def test_bitwise_ops(self):
        executor = OpcodeExecutor()
        executor.executeCommand("banr", [0, 1, 3], initial=[2, 1, 1, 1])
        self.assertEqual([2, 1, 1, 0], executor.registers)
        executor.executeCommand("bani", [0, 1, 3], initial=[2, 0, 1, 1])
        self.assertEqual([2, 0, 1, 0], executor.registers)

    def test_set(self):
        executor = OpcodeExecutor()
        executor.executeCommand("seti", [9, 1, 1], initial=[2, 1, 1, 1])
        self.assertEqual([2, 9, 1, 1], executor.registers)
        executor.executeCommand("setr", [0, 1, 1], initial=[2, 1, 1, 1])
        self.assertEqual([2, 2, 1, 1], executor.registers)

    def test_gt(self):
        executor = OpcodeExecutor()
        executor.executeCommand("gtir", [9, 1, 2], initial=[4, 3, 2, 1])
        self.assertEqual([4, 3, 1, 1], executor.registers)
        executor.executeCommand("gtri", [0, 1, 1], initial=[4, 3, 2, 0])
        self.assertEqual([4, 1, 2, 0], executor.registers)
        executor.executeCommand("gtrr", [2, 1, 0], initial=[4, 3, 2, 1])
        self.assertEqual([0, 3, 2, 1], executor.registers)

    def test_aoc(self):
        testCase = OpcodeTestCase.parse(
            "Before: [3, 2, 1, 1]", "9 2 1 2", "After:  [3, 2, 2, 1]")
        possibles = testCase.get_possible_opcodes()
        self.assertListEqual(["addi", "mulr", "seti"], possibles)


if __name__ == '__main__':
    unittest.main()
