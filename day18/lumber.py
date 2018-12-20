from collections import Counter


def main():
    board = Board()

    input = open("day18input.txt")
    map(board.add_row, input)

    print board.width, ", ", board.height

    for i in xrange(1000000000):
        if i % 1000 == 0:
            print "minute ", i
        board.do_tick()

    print board.get_resource_value()


class Board:
    def __init__(self):
        self.width = 0
        self.height = 0
        self.state = ""
        self.memos = {}

    def add_row(self, row):
        self.state += row.strip()
        if (self.width == 0):
            self.width = len(row.strip())
        self.height += 1

    def do_tick(self):

        if (self.state in self.memos):
            self.state = self.memos[self.state]
            return True

        new_state = []
        # go through each character and figure out the new state.
        for y in xrange(0, self.height):
            for x in xrange(0, self.width):
                new_char = self.get_new(self.get_char(x, y), x, y)
                new_state.append(new_char)

        if (not self.state in self.memos):
            self.memos[self.state] = ''.join(new_state)

        self.state = self.memos[self.state]
        return False

    def get_char(self, x, y):
        return self.state[x + (y * self.width)]

    def get_new(self, current, x, y):
        # get the valid neighbors
        tree_count = 0
        lumber_count = 0
        for new_x in xrange(x - 1, x + 2):
            for new_y in xrange(y - 1, y + 2):
                if (new_x >= 0 and new_x < self.width) and \
                        (new_y >= 0 and new_y < self.height) and \
                        (new_x != x or new_y != y):
                    neighbor = self.get_char(new_x, new_y)
                    if (neighbor == '|'):
                        tree_count += 1
                    if (neighbor == '#'):
                        lumber_count += 1

        # print "{0},{1}: {2}".format(x, y, current)

        if (current == '.'):
            if tree_count >= 3:
                return '|'
        elif (current == '|'):
            if lumber_count >= 3:
                return '#'
        elif (current == '#'):
            if tree_count >= 1 and lumber_count >= 1:
                return '#'
            else:
                return '.'

        return current

    def print_board(self):
        for row in self.get_board():
            print row

    def get_board(self):
        output = []
        for row_index in xrange(self.height):
            start_index = row_index * self.width
            row = self.state[start_index:start_index + self.width]
            output.append(row)
        return output

    def get_resource_value(self):
        num_woods = 0
        num_lumber = 0
        for row in self.state:
            row_counter = Counter(list(row))
            num_woods += row_counter['|']
            num_lumber += row_counter['#']
        return num_woods * num_lumber


if __name__ == '__main__':
    main()
