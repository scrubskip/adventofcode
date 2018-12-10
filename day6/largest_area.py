#!/usr/bin/env python
#

from parse import compile


def main():
    points_file = open("day6input.txt", "r")

    points = parse_points(points_file.readlines())

    vrange = get_visible_range(points)
    distance_matrix = make_matrix(points, vrange)
    find_largest_internal(distance_matrix, points)


def parse_points(point_strings):
    p = compile("{x:d}, {y:d}")
    return_list = []
    for point_str in point_strings:
        data = p.parse(point_str)
        if (data):
            return_list.append(Point(int(data['x']), int(data['y'])))

    return return_list


def get_margin():
    return 10


def get_visible_range(points):
    margin = get_margin()
    max_x = max(points, key=lambda point: point.x).x + margin
    min_x = min(points, key=lambda point: point.x).x - margin
    max_y = max(points, key=lambda point: point.y).y + margin
    min_y = min(points, key=lambda point: point.y).y - margin

    return [Point(min_x, min_y), Point(max_x, max_y)]


def make_matrix(points, vrange):
    distance_matrix = [[None] * (vrange[1].y - vrange[0].y)
                       for _ in range(vrange[1].x - vrange[0].x)]
    for i in range(vrange[0].x, vrange[1].x):
        for j in range(vrange[0].y, vrange[1].y):
            candidate_point = Point(i, j)
            min_distance = None
            stored_value = None
            for point in points:
                distance = calculate_distance(candidate_point, point)
                if min_distance == distance:
                    # found a dup
                    stored_value = '.'
                if min_distance is None or distance < min_distance:
                    min_distance = distance
                    stored_value = point

            # print "min_distance {0} to {1} is {2}".format(candidate_point, stored_value, min_distance)
            distance_matrix[i - vrange[0].x][j - vrange[0].y] = stored_value
    return distance_matrix


def make_matrix2(points, vrange):
    distance_matrix = [[None] * (vrange[1].y - vrange[0].y)
                       for _ in range(vrange[1].x - vrange[0].x)]


def calculate_distance(point_src, point_dst):
    return abs(point_src.x - point_dst.x) + abs(point_src.y - point_dst.y)


def find_largest_internal(distance_matrix, points):
    # iterate: any points on the edges are not candidates
    infinite_points = set()
    cumulative_area = {}
    for i in range(0, len(distance_matrix)):
        for j in range(0, len(distance_matrix[i])):
            point = distance_matrix[i][j]
            if (point == '.'):
                continue
            if (i == 0
                or j == 0
                or i == len(distance_matrix) - 1
                    or j == len(distance_matrix[i]) - 1):
                # print "Adding ", point, " because of ", i, ",", j
                infinite_points.add(distance_matrix[i][j])

            if (point not in cumulative_area):
                cumulative_area[point] = 0
            cumulative_area[point] += 1

    candidate_points = filter(
        lambda x: x not in infinite_points, cumulative_area)
    print "Infinite points: ", infinite_points
    points_and_areas = map(lambda x: (x, cumulative_area[x]), candidate_points)
    points_and_areas.sort(key=lambda x: x[1])
    print "Candidate points: ", points_and_areas
    max_area = max(filter(lambda x: x not in infinite_points,
                          cumulative_area), key=cumulative_area.get)
    print max_area, cumulative_area[max_area]
    return cumulative_area[max_area]


class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __repr__(self):
        return "{0},{1}".format(self.x, self.y)


if __name__ == '__main__':
    main()
