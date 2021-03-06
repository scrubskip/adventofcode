
def main():
    pass


# Sorts y coordinate first, then x.
def POSITION_COMPARATOR(x): return (x[1], x[0])


class Board:
    def __init__(self):
        self.state = []
        self.width = -1
        self.units = []

    def add_row(self, row):
        self.state.append(row)
        if (self.width == -1):
            self.width = len(row)
        elif (self.width != len(row)):
            raise ValueError(
                "Invalid row: doesn't match width " + self.width + ": " + len(row))
        self.height = len(self.state)
        # search for units in the new row
        for index in xrange(self.width):
            if (Unit.is_unit(row[index])):
                unit = Unit(row[index], (index, self.height - 1))
                self.units.append(unit)

    def do_tick(self):
        # sort into reading order
        self.units.sort(key=POSITION_COMPARATOR)
        for unit in self.units:
            unit.update(self)

    def is_empty(self, position):
        return self.state[position[1]][position[0]] == '.'

    def is_target(self, type, position):
        unit_type = 'G' if type == 'E' else 'E'
        return self.state[position[1]][position[0]] == unit_type

    @staticmethod
    def parse(input_lines):
        board = Board()
        for line in input_lines:
            board.add_row(line)
        return board


class Unit:
    def __init__(self, type, position):
        self.hp = 200
        self.attack = 3
        self.position = position
        self.type = type

    def update(self, board):
        targets = self.get_targets(board)
        if (len(targets) == 0):
            # no more targets!
            return
        targets.sort(key=lambda x: x.position)
        positions = self.get_adjacent_positions()
        # are any targets in the positions? If so, attack.
        for position in positions:
            if board.is_target(self.type, position):
                self.attack_target(targets.find(
                    lambda x: x.position == position))
                return

        # OK, at this point need to move. Get the possible moves spaces, then
        # BFS towards them.
        possible_move_spaces = self.get_possible_move_spaces(targets, board)

    def get_targets(self, board):
        return filter(lambda x: x.type != self.type, board.units)

    def attack_target(self, target):
        pass

    def get_possible_move_spaces(self, targets, board):
        """Returns the possible move spaces among the targets.
        Assumes the targets are in reading order and returns the spaces in reading order.
        """
        return_list = []
        for target in targets:
            for position in target.get_adjacent_positions():
                if (board.is_empty(position)):
                    return_list.append(position)
        return_list.sort(key=POSITION_COMPARATOR)
        return return_list

    def get_adjacent_positions(self):
        x = self.position[0]
        y = self.position[1]
        return [(x, y - 1), (x - 1, y), (x + 1, y), (x, y + 1)]

    def __repr__(self):
        return "{0}({2}): {1}".format(self.type, self.position, self.hp)

    @staticmethod
    def is_unit(str):
        return str == "G" or str == "E"


if __name__ == "__main__":
    main()
