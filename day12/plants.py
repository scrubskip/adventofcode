from parse import compile


def main():
    input = open("day12input.txt", "r")
    state = input.readline().substring(len("initial state: "))
    rules = filter(map(PlantRule.parse, input.readlines))
    print state,
    print rules


class PlantArray:
    def __init__(self, initial_state):
        self.state = initial_state
        self.zero_index = 0

    def get_plant_at_index(self, index):
        real_index = self.zero_index + index
        return self.state[real_index]

    def apply_rules(self, rules):
        changes = {}
        for rule in rules:
            for change in rule.get_changes(self.state):
                changes[change[0]] = change[1]


class PlantRule:
    PARSER = compile("{0} => {1}")

    def __init__(self, pattern, end_state):
        self.pattern = pattern
        self.end_state = end_state

    def get_changes(self, state):
        """Return an array of tuples where the first item is the real index and the second item is the end state

        For example, [(0, "#"), (6, ".")] means the changes are change 0 to "#" and 6 to "."
        """
        changes = []
        index = state.find(self.pattern)
        while index != -1:
            changes.append((index + 2, self.end_state))
            index = state.find(self.pattern, index + 1)
        return changes

    @staticmethod
    def parse(input_str):
        data = PlantRule.PARSER.parse(input_str)
        if data:
            return PlantRule(data[0], data[1])
        return None


if __name__ == "__main__":
    print main()
