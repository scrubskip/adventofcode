from parse import compile


def main():
    ground = Ground()
    input = open("day17input.txt", "r")
    map(ground.add_input, input)
    ground.emit_full()
    ground.print_board()
    print ground.get_reachable()
    print len(ground.settled_water)


class Ground:
    def __init__(self):
        self.clays = set()
        self.settled_water = set()
        self.reachable = set()
        self.seen_emitters = set()
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

    def emit_full(self):
        emitters = []
        emitters.append((500, 1))
        while (len(emitters) > 0):
            new_emitters = self.emit_water(emitters.pop(0))
            map(emitters.append, new_emitters)

    def emit_water(self, current_position=(500, 1)):
        """Emits water, filling spaces and returns more candidate emitters or None if there are none
        """

        if (current_position in self.seen_emitters):
            print "Skipping ", current_position
            return []

        self.seen_emitters.add(current_position)
        print "emitting at ", current_position

        # fall until the first clay or water.
        while current_position[1] <= self.max_y and \
                self.is_sand((current_position[0], current_position[1] + 1)):
            self.reachable.add(current_position)
            current_position = (current_position[0], current_position[1] + 1)

        if (current_position[1] >= self.max_y):
            # past the edge.
            return []

        # OK, found a blockage downward. Flow left and right.
        print "Found blockage ", current_position
        left, left_wall = self.find_wall(current_position, -1)
        right, right_wall = self.find_wall(current_position, +1)

        # fill if we have a left and right wall.
        while (left_wall and right_wall):
            self.fill_space(left, right)
            current_position = (
                current_position[0], current_position[1] - 1)
            left, left_wall = self.find_wall(current_position, -1)
            right, right_wall = self.find_wall(current_position, +1)

        # now look for the overflows and return them
        return_vals = []
        if (not left_wall):
            return_vals.append(left)
        if (not right_wall):
            return_vals.append(right)
        return return_vals

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

    def get_reachable(self):
        num_reachable = 0
        for y in xrange(self.min_y, self.max_y + 1):
            for x in xrange(self.min_x, self.max_x + 2):
                position = (x, y)
                if (self.is_settled_water(position)):
                    num_reachable += 1
                elif (position in self.reachable):
                    num_reachable += 1
        return num_reachable


if __name__ == "__main__":
    main()
