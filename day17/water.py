from parse import compile


def main():
    ground = Ground()
    input = open("day17input.txt", "r")
    map(ground.add_input, input)

    ground.print_board()
    print len(ground.reachable)


class Ground:
    def __init__(self):
        self.clays = set()
        self.settled_water = set()
        self.reachable = set()
        self.min_x = 495
        self.max_x = 507
        self.min_y = -1
        self.max_y = -1

    INPUT_MATCHER = compile(
        "{fixed_axis:w}={fixed:d}, {range_axis:w}={range_start:d}..{range_end:d}")

    def add_input(self, input):
        """Adds input in the form "x=495, y=2..7"
        """
        clay_range = Ground.INPUT_MATCHER.parse(input.strip())
        if (clay_range):
            range_vals = range(
                clay_range['range_start'], clay_range['range_end'] + 1)
            fixed_val = clay_range['fixed']
            if (clay_range['fixed_axis'] == 'x'):
                ranges = map(lambda y: (fixed_val, y), range_vals)
                self.add_clay_ranges(ranges)
            elif (clay_range['fixed_axis'] == 'y'):
                ranges = map(lambda x: (x, fixed_val), range_vals)
                self.add_clay_ranges(ranges)

    def add_clay_ranges(self, ranges):
        for range in ranges:
            self.clays.add(range)
            if (range[0] < self.min_x):
                self.min_x = range[0]
            if (range[0] > self.max_x):
                self.max_x = range[0]
            if (range[1] > self.max_y):
                self.max_y = range[1]
            if (range[1] < self.min_y or self.min_y == -1):
                self.min_y = range[1]

    def is_clay(self, point):
        """Returns True is the point is clay.
        """
        return point in self.clays

    def is_settled_water(self, point):
        return point in self.settled_water

    def is_sand(self, point):
        return not (self.is_clay(point) or self.is_settled_water(point))

    def emit_water(self, current_position=(500, 1)):
        print "emitting at ", current_position
        original_position = current_position
        # fall until the first clay or water.
        while current_position[1] <= self.max_y and \
                self.is_sand((current_position[0], current_position[1] + 1)):
            self.reachable.add(current_position)
            current_position = (current_position[0], current_position[1] + 1)

        # OK, found a blockage downward. Flow left and right.
        left, left_wall = self.find_wall(current_position, -1)
        right, right_wall = self.find_wall(current_position, +1)
        print "Found items ", left, ", ", right
        if (left_wall and right_wall):
            self.fill_space(left, right)
            self.emit_water(original_position)
        if (not right_wall):
            # found something on the left but not right. emit on the right
            self.emit_water(right)
        if (not left_wall):
            self.emit_water(left)

    def find_wall(self, position, delta):
        """Return the first wall or dropoff in the current direction
        value of the position, True if it's a wall, false if it's a dropdown
        """
        self.reachable.add(position)
        while not (self.is_clay((position[0] + delta, position[1]))):
            # print "Checking wall", position
            self.reachable.add(position)
            if (self.is_sand((position[0] + delta, position[1] + 1))):
                # no bottom! fall down
                position = (position[0] + delta, position[1])
                return position, False
            position = (position[0] + delta, position[1])

        self.reachable.add(position)
        return position, True

    def fill_space(self, left_position, right_position):
        if (left_position[1] != right_position[1]):
            raise ValueError("Left and right positions don't match!")
        for x in xrange(left_position[0], right_position[0] + 1):
            position = (x, left_position[1])
            self.settled_water.add(position)

    def print_board(self):
        for y in xrange(0, self.max_y + 1):
            row = []
            for x in xrange(self.min_x, self.max_x + 2):
                character = "."
                position = (x, y)
                if (self.is_clay(position)):
                    character = "#"
                elif (self.is_settled_water(position)):
                    character = "~"
                elif (position in self.reachable):
                    character = "|"
                row.append(character)
            print ''.join(row)


if __name__ == "__main__":
    main()
