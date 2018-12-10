from parse import compile


def main():
    points_file = open("day10input.txt", "r")
    points = []
    for point_str in points_file.readlines():
        point = Point.parse(point_str)
        if (point):
            points.append(point)

    time = 0
    first_visible_time = -1
    while True:
        print "Time: ", time
        matrix, found_visible = get_points_at_time(points, time)
        if (found_visible):
            print_matrix(matrix)
            first_visible_time = time
        elif first_visible_time > -1:
            if (time - first_visible_time) > 20:
                break
        time += 1
        


def get_visible_range():
    return [[0, 0], [100, 100]]

def guess_time(points):
    # for each time, check when the coordinates are near one another.
    1 + 1

def get_points_at_time(points, time):
    vrange = get_visible_range()
    matrix = [['.'] * (vrange[1][1] - vrange[0][1])
              for _ in range(vrange[1][0] - vrange[0][0])]
    visible_count = 0
    for point in points:
        x = point.x + (time * point.dx)
        y = point.y + (time * point.dy)
        if (x < vrange[1][0] - vrange[0][0]) and \
           (y < vrange[1][1] - vrange[0][1] and \
           x >=0 and y>= 0):
            matrix[x - vrange[0][0]][y - vrange[0][1]] = '#'
            visible_count += 1

    return matrix, visible_count == len(points)

def print_matrix(matrix):
    for j in range(len(matrix)):
        row = []
        for i in range(len(matrix[j])):
            row.append(matrix[i][j])
        print row


class Point:
    PARSER = compile("position=<{x:d}, {y:d}> velocity=<{dx:d}, {dy:d}>")

    def __init__(self, x, y, dx, dy):
        self.x = x
        self.y = y
        self.dx = dx
        self.dy = dy

    @staticmethod
    def parse(point_str):
        data = Point.PARSER.parse(point_str)
        # print data
        if (data):
            return Point(data['x'], data['y'], data['dx'], data['dy'])
        return None


if __name__ == '__main__':
    main()
