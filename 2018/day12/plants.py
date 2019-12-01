from parse import compile


def main():
    input = open("day12input.txt", "r")
    state = input.readline()[len("initial state: "):]
    state = state.strip()
    rules = filter(None, map(PlantRule.parse, input.readlines()))
    rules = filter(lambda x: x.end_state == '#', rules)

    print state
    print rules
    print len(rules)
    plants = PlantArray(state)
    print "Gen  0:", ("..." + plants.state)
    delta_num = 0
    old_plant_num = 0
    for i in range(1, 1001):
        plants.apply_rules(rules)
        delta_num = plants.get_plant_num() - old_plant_num
        old_plant_num = plants.get_plant_num()
        print "Gen {0:2d}: {1} {2}".format(
            i, delta_num, plants.get_state_with_left_padding(3))

    # real answer: 4386 for 20
    offset = plants.get_plant_num() - (1000 * delta_num)
    return 50000000000 * delta_num + offset

    


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
        if (not self.state.startswith("....")):
            self.state = "...." + self.state
            self.zero_index += 4
        if (not self.state.endswith("....")):
            self.state = self.state + "...."

        for rule in rules:
            changes += rule.get_changes(self.state)
            # apply changes
        list_state = ["."] * len(self.state)
        changes.sort(key=lambda x: x[0])
        for change in changes:
            # print "Applying change ", change
            list_state[change[0]] = change[1]
        self.state = "".join(list_state)

    def get_plant_num(self):
        sum = 0
        for i in range(len(self.state)):
            plant_index = i - self.zero_index
            if (self.state[i] == '#'):
                sum += plant_index
                # print plant_index, " sum: ", sum
        return sum

    def get_state_with_left_padding(self, padding):
        return self.state[self.zero_index - padding:]

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
