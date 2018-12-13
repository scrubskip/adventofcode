from parse import compile


def main():
    input = open("day12input.txt", "r")
    state = input.readline()[len("initial state: "):]
    rules = filter(None, map(PlantRule.parse, input.readlines()))
    print state
    print rules
    plants = PlantArray(state)
    for i in range(1, 21):
        print "Gen ", i
        plants.apply_rules(rules)

    print plants
    return plants.get_plant_num()


class PlantArray:
    def __init__(self, initial_state):
        self.state = initial_state
        self.zero_index = 0

    def get_plant_at_index(self, index):
        real_index = self.zero_index + index
        return self.state[real_index]

    def apply_rules(self, rules):
        changes = []
        # append some empty pots to the front to help match
        self.state = "...." + self.state + "...."
        self.zero_index += 4
        for rule in rules:
            changes += rule.get_changes(self.state)
            # apply changes
        list_state = list(self.state)
        for change in changes:
            #print "Applying change ", change
            list_state[change[0]] = change[1]
        self.state = "".join(list_state)

    def get_plant_num(self):
        sum = 0
        for i in range(len(self.state)):
            plant_index = i - self.zero_index
            if (self.state[i] == '#'):
                sum += plant_index
                print plant_index, " sum: ", sum
        return sum

    def __repr__(self):
        return "{0}: {1}".format(self.zero_index, self.state)


class PlantRule:
    PARSER = compile("{0} => {1}")

    def __init__(self, pattern, end_state):
        self.pattern = pattern
        self.end_state = end_state

    def get_changes(self, state):
        """Return an array of tuples where the first item is the real index and the second item is the end state

        For example, [(0, "#"), (6, "#")] means the changes are change 0 to "#" and 6 to "#"
        """
        changes = []
        index = state.find(self.pattern)
        while index != -1:
            changes.append((index + 2, self.end_state))
            index = state.find(self.pattern, index + 1)
        return changes

    def __repr__(self):
        return self.pattern + " => " + self.end_state

    @staticmethod
    def parse(input_str):
        data = PlantRule.PARSER.parse(input_str)
        if data:
            return PlantRule(data[0], data[1])
        return None


if __name__ == "__main__":
    print main()
