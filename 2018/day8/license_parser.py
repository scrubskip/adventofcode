#!/usr/bin/env python
#


def main():
    license_file = open("day8input.txt", "r")
    license = license_file.readline()
    license_node = parse_node(map(int, license.split()))
    print license_node.get_reference_sum()


def parse_node(license_entries):
    if (len(license_entries) < 2):
        return None
    num_child_nodes = license_entries.pop(0)
    num_metadata = license_entries.pop(0)
    node = Node()
    for i in range(num_child_nodes):
        node.add_node(parse_node(license_entries))

    for i in range(num_metadata):
        node.add_metadata(license_entries.pop(0))

    return node


class Node:
    def __init__(self):
        self.nodes = []
        self.metadata = []

    def add_node(self, node):
        self.nodes.append(node)

    def add_metadata(self, entry):
        self.metadata.append(entry)

    def get_sum(self):
        return sum(map(lambda x: x.get_sum(), self.nodes)) \
            + sum(self.metadata)

    def get_reference_sum(self):
        if (len(self.nodes) == 0):
            return sum(self.metadata)
        else:
            reference_sum = 0
            for entry in self.metadata:
                entry_index = entry - 1
                if (entry_index < len(self.nodes) and entry_index >= 0):
                    reference_sum += self.nodes[entry_index].get_reference_sum()
            return reference_sum


if __name__ == '__main__':
    main()
