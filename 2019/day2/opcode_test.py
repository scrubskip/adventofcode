import opcode
import unittest

class OpcodeTestCase(unittest.TestCase):
    def test_simple(self):
        input_data = [1,9,10,3,2,3,11,0,99,30,40,50]
        output_data = [3500,9,10,70,2,3,11,0,99,30,40,50]
        
        self.assertListEqual(output_data, opcode.run(input_data))

if __name__ == '__main__':
    unittest.main()
