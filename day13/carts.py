from collections import Counter


def main():
    track_lines = open("day13input.txt", "r")
    track = Track.parse(track_lines.readlines())
    while not track.do_tick():
        1+1


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
            self.state[row][index] = segment_str
            index += 1

    def extract_carts(self):
        """Update the state with any carts found.

        Return the new carts with their initial position and direction set
        """
        self.carts = []
        for i in xrange(self.width):
            for j in xrange(self.height):
                current = self.state[j][i]
                if (Cart.is_cart_str(current)):
                    # which segment is it really?
                    cart = Cart((i, j), current)
                    if (current == '<' or current == '>'):
                        current = "-"
                    if (current == "^" or current == "v"):
                        current = "|"
                    self.state[j][i] = current
                    self.carts.append(cart)
        return self.carts

    def do_tick(self):
        self.carts.sort(key=lambda x: x.position)
        for cart in self.carts:
            cart.update(self.state)
            for other_cart in self.carts:
                if (cart.position == other_cart.position and cart != other_cart):
                    print "Crash at {0}".format(cart.position)
                    return True
        return False

    def print_track(self):
        for row in self.state:
            print row


class Cart:
    def __init__(self, initial_position, initial_direction):
        self.position = initial_position
        self.direction = initial_direction
        self.next_turn = 'l'

    def update(self, state):
        old_cell = state[self.position[1]][self.position[0]]
        delta_x = 0
        delta_y = 0
        if (self.direction == "<"):
            delta_x = -1
        elif (self.direction == ">"):
            delta_x = 1
        elif (self.direction == "^"):
            delta_y = -1
        elif (self.direction == "v"):
            delta_y = 1
        else:
            raise RuntimeError("No direction " + self.direction)

        self.position = (self.position[0] + delta_x,
                         self.position[1] + delta_y)
        # Now update the direction based on the new state
        new_cell = state[self.position[1]][self.position[0]]
        if (new_cell == "+"):
            # turn based on next_turn
            self.direction = Cart.get_turn_direction(
                self.direction, self.next_turn)
            self.next_turn = self.get_next_turn()
        if (new_cell == "\\"):
            if (old_cell == "-"):
                if (delta_x == 1):
                    self.direction = "v"
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
                if (delta_x == -1):
                    self.direction = "v"
                elif (delta_x == 1):
                    self.direction = "^"
                else:
                    raise RuntimeError("Bad direction at {0} new cell {1} from old cell {2}".format(
                        self.position, new_cell, old_cell))
            elif (old_cell == "|"):
                if (delta_y == -1):
                    self.direction = ">"
                elif (delta_y == 1):
                    self.direction = "<"
                else:
                    raise RuntimeError("Bad direction at {0} new cell {1} from old cell {2}".format(
                        self.position, new_cell, old_cell))

    DIRECTIONS = ["<", "^", ">", "v"]
    TURNS = ['l', 's', 'r']

    @staticmethod
    def is_cart_str(str):
        return str in Cart.DIRECTIONS

    @staticmethod
    def get_turn_direction(direction, turn):
        if (turn == 's'):
            return direction
        index = Cart.DIRECTIONS.index(direction)
        if (turn == 'l'):
            direction = Cart.DIRECTIONS[(index - 1) % 4]
        if (turn == 'r'):
            direction = Cart.DIRECTIONS[(index + 1) % 4]
        return direction

    def get_next_turn(self):
        if (self.next_turn == 'l'):
            return 's'
        elif (self.next_turn == 's'):
            return 'r'
        elif (self.next_turn == 'r'):
            return 'l'
        else:
            raise RuntimeError("Unknown turn " + self.next_turn)

    def __repr__(self):
        return "{0}: {1}".format(self.position, self.direction)


if __name__ == "__main__":
    print main()
