from parse import compile


def main():
    points_file = open("day10input.txt", "r")
    points = []
    for point_str in points_file.readlines():
        point = Point.parse(point_str)
        if (point):
            points.append(point)

    print guess_time(points, 10630)
    time = 10634
    matrix = get_matrix_at_time(points, time)
    print_matrix(matrix, get_visible_range(get_points_at_time(points, time)))


def get_visible_range(points):
    margin = 4
    max_x = max(points, key=lambda point: point.x).x + margin
    min_x = min(points, key=lambda point: point.x).x - margin
    max_y = max(points, key=lambda point: point.y).y + margin
    min_y = min(points, key=lambda point: point.y).y - margin

    return [Point(min_x, min_y, 0, 0), Point(max_x, max_y, 0, 0)]


def guess_time(points, start_time):
    # for each time, check when the coordinates are near one another.
    current_points = []
    min_bounding_box_area = None
    delta_area = 0
    time = start_time
    min_time = 0

    while True:

        current_points = get_points_at_time(points, time)

        vrange = get_visible_range(current_points)
        area = (vrange[1].x - vrange[0].x) * (vrange[1].y - vrange[0].y)
        print "Area: ", area, " time: ", time
        if (area < min_bounding_box_area or min_bounding_box_area is None):
            min_bounding_box_area = area
            min_time = time

        if (min_bounding_box_area is not None and area > min_bounding_box_area):
            print "Starting to increase from ", min_bounding_box_area, " to ", area
            print time
            break

        time += 1

    return min_time


def get_points_at_time(points, time):
    current_points = []
    for point in points:
        x = point.x + (time * point.dx)
        y = point.y + (time * point.dy)
        current_points.append(Point(x, y, 0, 0))
    return current_points


def get_matrix_at_time(points, time):
    current_points = get_points_at_time(points, time)
    vrange = get_visible_range(current_points)
    matrix = [['.'] * (vrange[1].y - vrange[0].y + 1)
              for _ in range(vrange[1].x - vrange[0].x + 1)]
    for point in current_points:
        x = point.x
        y = point.y
        matrix[x - vrange[0].x][y - vrange[0].y] = '#'

    return matrix


def print_matrix(matrix, vrange):
    print vrange
    for j in range(vrange[0].y, vrange[1].y):
        row = []
        for i in range(vrange[0].x, vrange[1].x):
            row.append(matrix[i - vrange[0].x][j - vrange[0].y])
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

    def __repr__(self):
        return "{0},{1} [{2}, {3}]".format(self.x, self.y, self.dx, self.dy)


if __name__ == '__main__':
    main()
