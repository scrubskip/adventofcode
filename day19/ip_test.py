import unittest
import ip
from ip import OpcodeExecutor


class IpTest(unittest.TestCase):
    def test_parse(self):
        input = [
            "#ip 1",
            "seti 5 0 1"
        ]
        executor = OpcodeExecutor()
        executor.load_program(input)

        self.assertEquals(1, executor.ip_register)
        self.assertEquals(1, len(executor.program))
        instruction = executor.program[0]
        self.assertEquals("seti", instruction[0])
        self.assertEqual([5, 0, 1], instruction[1])

    def test_aoc(self):
        input = [
            "#ip 0",
            "seti 5 0 1",
            "seti 6 0 2",
            "addi 0 1 0",
            "addr 1 2 3",
            "setr 1 0 0",
            "seti 8 0 4",
            "seti 9 0 5"
        ]
        executor = OpcodeExecutor()
        executor.load_program(input)
        self.assertEquals(0, executor.ip_register)
        self.assertEquals(7, len(executor.program))
        executor.run_program()


if __name__ == "__main__":
    unittest.main()
