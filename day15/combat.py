
def main():
    pass


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
        self.units.sort(key=lambda x: x.position)
        for unit in self.units:
            unit.update(self)

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
        targets = filter(lambda x: x.type != self.type, board.units)
        if (len(targets) == 0):
            # no more targets!
            return
        positions = self.get_adjacent_positions()
        # are any targets in the positions? If so, attack.

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
