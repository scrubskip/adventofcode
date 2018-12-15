
def main():
    input = 30121
    board = Board()
    print board.get_ten_after(input)

class Board:
    def __init__(self):
        self.elf1_index = 0
        self.elf2_index = 1
        self.state = [3, 7]

    def get_ten_after(self, input):
        num_recipes = len(self.state)
        while num_recipes < input + 10:
            num_recipes = self.do_tick()

        return reduce(lambda x,y: x+y,
            map(lambda x: str(x),
            self.state[input:input+10]))

    def find_string(self, input):
        pass

    def do_tick(self):
        score1 = self.state[self.elf1_index]
        score2 = self.state[self.elf2_index]
        combined = score1 + score2
        if (combined >= 10):
            map(lambda x: self.state.append(int(x)), str(combined))
        else:
            self.state.append(combined)

        self.elf1_index = (self.elf1_index + score1 + 1) % len(self.state)
        self.elf2_index = (self.elf2_index + score2 + 1) % len(self.state)

        return len(self.state)

    def __repr__(self):
        return "{0}, {1}: {2}".format(self.elf1_index, self.elf2_index, self.state)


if __name__ == "__main__":
    main()
