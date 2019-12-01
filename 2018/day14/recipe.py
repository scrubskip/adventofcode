
def main():
    input = 30121
    board = Board()
    print board.get_ten_after(input)
    print board.find_string('030121')

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
        finder = SequenceFinder(input)
        map(lambda x: finder.input(str(x)), self.state)
        while True:
            self.do_tick()
            for new_recipe in self.last_added:
                found, index = finder.input(new_recipe)
                if (found):
                    return finder.seen_characters - len(input)
        
            
    def do_tick(self):
        score1 = self.state[self.elf1_index]
        score2 = self.state[self.elf2_index]
        combined = score1 + score2
        if (combined >= 10):
            map(lambda x: self.state.append(int(x)), str(combined))
        else:
            self.state.append(combined)
        
        self.last_added = list(str(combined))
        self.elf1_index = (self.elf1_index + score1 + 1) % len(self.state)
        self.elf2_index = (self.elf2_index + score2 + 1) % len(self.state)

        return len(self.state)

    def __repr__(self):
        return "{0}, {1}: {2}".format(self.elf1_index, self.elf2_index, self.state)

class SequenceFinder:
    def __init__(self, input):
        self.sequence = list(input)
        self.index = 0
        self.seen_characters = 0
    
    def input(self, character):
        self.seen_characters += 1
        if (self.sequence[self.index] == character):
            self.index += 1
            if (self.index >= len(self.sequence)):
                return True, self.index
        else:
            self.index = 0
            # The character that reset might be the sequence starter
            if (self.sequence[self.index] == character):
                self.index += 1
        return False, self.index

if __name__ == "__main__":
    main()
