def main():
    print "hi"


class Track:

    @staticmethod
    def parse(input_lines):
        track = Track(len(input_lines[0]), len(input_lines))
        for i in xrange(len(input_lines)):
            track.add_row(i, list(input_lines[i]))

        # now go find the carts and update the segments
        track.extract_carts()

        return track

    def __init__(self, width, height):
        self.state = [[' '] * width for _ in range(height)]
        self.width = width
        self.height = height
        self.carts = []

    def add_row(self, row, segment_strs):
        index = 0
        for segment_str in segment_strs:
            self.state[index][row] = segment_str
            index += 1

    def extract_carts(self):
        """Update the state with any carts found.

        Return the new carts with their initial position and direction set
        """
        self.carts = []
        for i in xrange(self.width):
            for j in xrange(self.height):
                current = self.state[i][j]
                if (Cart.is_cart_str(current)):
                    # which segment is it really?
                    cart = Cart((i, j), current)
                    if (current == '<' or current == '>'):
                        current = "-"
                    if (current == "^" or current == "V"):
                        current = "|"
                    self.state[i][j] = current
                    self.carts.append(cart)
        return self.carts

    def do_tick(self):
        for cart in self.carts:
            cart.update(self.state)


class Cart:
    def __init__(self, initial_position, initial_direction):
        self.position = initial_position
        self.direction = initial_direction
        self.next_turn = 'l'

    def update(self, state):
        old_cell = state[self.position[0]][self.position[1]]
        delta_x = 0
        delta_y = 0
        if (self.direction == "<"):
            delta_x = -1
        elif (self.direction == ">"):
            delta_x = 1
        elif (self.direction == "^"):
            delta_y = -1
        elif (self.direction == "V"):
            delta_y = 1
        else:
            raise RuntimeError("No direction " + self.direction)

        self.position = (self.position[0] + delta_x,
                         self.position[1] + delta_y)
        # Now update the direction based on the new state
        new_cell = state[self.position[0]][self.position[1]]
        if (new_cell == "+"):
            # turn based on next_turn
        if (new_cell == "\\"):
            if (old_cell == "-"):
                if (delta_x == 1):
                    self.direction = "V"
                elif (delta_x == -1):
                    self.direction = "^"
                else:
                    raise RuntimeError("Bad direction at {0} new cell {1} from old cell {2}".format(
                        self.position, new_cell, old_cell))
            elif (old_cell == "|"):
                if (delta_y == -1):
                    self.direction = "<"
                elif (delta_y == 1):
                    self.direction = ">"
                else:
                    raise RuntimeError("Bad direction at {0} new cell {1} from old cell {2}".format(
                        self.position, new_cell, old_cell))
        if (new_cell == "/"):
            if (old_cell == "-"):
                self.direction = "^"
            elif (old_cell == "|"):
                self.direction = "<"

    @staticmethod
    def is_cart_str(str):
        return str in ["<", ">", "^", "V"]


if __name__ == "__main__":
    print main()
