

def main():
    game = MarbleGame(452, 71250)
    game.run_game()
    print game.get_high_score()


class MarbleGame:
    def __init__(self, num_players, num_marbles):
        self.board = [0]
        self.current_marble_index = 0
        self.num_players = num_players
        self.player_scores = [0] * num_players
        self.current_player = 0
        self.num_marbles = num_marbles

    def run_game(self):
        for marble in range(self.num_marbles):
            progress = marble / float(self.num_marbles)
            print "progress:", progress
            self.run_step(marble + 1)

    def run_step(self, insert_marble):
        board_size = len(self.board)
        self.current_player = (self.current_player + 1) % self.num_players

        if (insert_marble % 23 == 0):
            self.player_scores[self.current_player] += insert_marble
            # take the marble 7 before
            remove_index = (self.current_marble_index - 7) % board_size
            self.player_scores[self.current_player] += self.board.pop(
                remove_index)
            self.current_marble_index = remove_index
        else:
            insert_index = (self.current_marble_index + 2) % board_size
            if (insert_index == 0):
                self.board.append(insert_marble)
                self.current_marble_index = board_size
            else:
                self.board.insert(insert_index, insert_marble)
                self.current_marble_index = insert_index

        # print "[", self.current_player, "] ", insert_marble, "@", self.current_marble_index, self.board

    def get_high_score(self):
        return max(self.player_scores)


if __name__ == '__main__':
    main()
