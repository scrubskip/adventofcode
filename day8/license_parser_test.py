import unittest
import license_parser

class LicenseTestCase(unittest.TestCase):
    
    def test_parse_once(self):
        license_entries = [0, 1, 1]
        node = license_parser.parse_node(license_entries)
        self.assertEquals(0, len(node.nodes))
        self.assertEquals(1, len(node.metadata))
        self.assertEquals(1, node.metadata[0])

    def test_parse_nested(self):
        license_entries = [1, 0, 0, 1, 2]
        node = license_parser.parse_node(license_entries)
        self.assertEquals(1, len(node.nodes))
        self.assertEquals(0, len(node.metadata))
        self.assertEquals(2, node.nodes[0].metadata[0])


    def test_parse_nested_more_meta(self):
        license_entries = [1, 3, 0, 1, 2, 7, 8, 9]
        node = license_parser.parse_node(license_entries)
        self.assertEquals(1, len(node.nodes))
        self.assertEquals(3, len(node.metadata))
        self.assertEquals(7, node.metadata[0])
        self.assertEquals(8, node.metadata[1])
        self.assertEquals(9, node.metadata[2])
        self.assertEquals(2, node.nodes[0].metadata[0])


if __name__ == '__main__':
    unittest.main()