def main():
    scanner = Scanner(5, 746, 4002)
    print scanner.get_risk_level_area(0, 0, 5, 746)


class Scanner:
    def __init__(self, target_x, target_y, depth):
        self.target_x = target_x
        self.target_y = target_y
        self.depth = depth
        self.geologic = {}
        self.erosion_level = {}

    def get_geologic_index(self, x, y):
        if ((x, y) in self.geologic):
            return self.geologic[(x, y)]

        returnVal = 0
        if (x == 0 and y == 0):
            returnVal = 0
        elif (x == self.target_x and y == self.target_y):
            returnVal = 0
        elif (y == 0):
            returnVal = 16807 * x
        elif (x == 0):
            returnVal = 48271 * y
        else:
            returnVal = self.get_erosion_level(x - 1, y) * self.get_erosion_level(x, y - 1)
        
        self.geologic[(x,y)] = returnVal

        return returnVal

    def get_erosion_level(self, x, y):
        return (self.get_geologic_index(x, y) + self.depth) % 20183

    def get_risk_level(self, x, y):
        return self.get_erosion_level(x,y) % 3

    def get_risk_level_area(self, start_x, start_y, end_x, end_y):
        risk_level = 0
        for x in xrange(start_x, end_x + 1):
            for y in xrange(start_y, end_y + 1):
                risk_level += self.get_risk_level(x, y)
        return risk_level


if __name__ == '__main__':
    main()