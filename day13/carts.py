def main():
    print "hi"


class Track:

    @staticmethod
    def parse(input_lines):
        track = Track(len(input_lines[0]), len(input_lines))
        for i in xrange(len(input_lines)):
            track.add_row(i, list(input_lines[i]))

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


class Cart:
    def __init__(self, initial_position, initial_direction):
        self.position = initial_position
        self.direction = initial_direction


if __name__ == "__main__":
    print main()
