from parse import compile


def main():
    points_file = open("day10input.txt", "r")
    points = []
    for point_str in points_file.readlines():
        point = Point.parse(point_str)
        if (point):
            points.append(point)


def get_visible_range():
    return [[0, 0], [25, 25]]


def get_points_at_time(points, time):
    vrange = get_visible_range()
    matrix = [['.'] * vrange[1][1] - vrange[0][1]
              for _ in range(vrange[1][0] - vrange[0][0])]
    found_visible = False
    for point in points:
        x = point.x + (time * point.dx)
        y = point.y + (time * point.dy)
        if (x < vrange[1][0] - vrange[0][0]) and \
           (y < vrange[1][1] - vrange[0][1]):
            matrix[x - vrange[0][0]][y - vrange[0][1]] = '#'
            found_visible = True

    return matrix, found_visible


class Point:
    PARSER = compile("position=<{x:d}, {y:d}> velocity=<{dx:d},{dy:d}>")

    def __init__(self, x, y, dx, dy):
        self.x = x
        self.y = y
        self.dx = dx
        self.dy = dy

    @staticmethod
    def parse(point_str):
        data = Point.PARSER.parse(point_str)
        if (data):
            return Point(data['x'], data['y'], data['dx'], data['dy'])
        return None


if __name__ == '__main__':
    main()
