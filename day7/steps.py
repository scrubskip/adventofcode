#!/usr/bin/env python
#

from parse import compile


def main():
    step_lines = open("day7input.txt", "r")

    nodes = parse_lines(step_lines.readlines())

    roots = find_roots(nodes)

    print roots


def parse_lines(lines):
    p = compile(
        "Step {label} must be finished before step {prereq} can begin.")
    nodes = {}
    for line in lines:
        data = p.parse(line)
        if (data is not None):
            label = data['label']
            prereq = data['prereq']

            if (label in nodes):
                node = nodes[label]
            else:
                node = Node(label)
                nodes[label] = node

            if (prereq not in nodes):
                prereq_node = Node(prereq)
                nodes[prereq] = prereq_node

            node.add_prereq(prereq)

    return nodes


def find_roots(nodes):
    return filter(lambda x: not x.has_prereqs,
                  map(nodes.get, nodes))


class Node:

    def __init__(self, label):
        self.label = label
        self.prereqs = []

    def add_prereq(self, prereq_label):
        self.prereqs.append(prereq_label)

    def has_prereqs(self):
        return len(self.prereqs) > 0

    def has_prereq(self, prereq):
        return prereq in self.prereqs


if __name__ == '__main__':
    main()
