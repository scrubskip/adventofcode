#!/usr/bin/env python
#

from parse import compile


def main():
    points_file = open("day6input.txt", "r")

    points = parse_points(points_file.readlines())


def parse_points(point_strings):
    p = compile("{x:d}, {y:d}")
    return_list = []
    for point_str in point_strings:
        data = p.parse(point_str)
        if (data):
            return_list.append(Point(int(data['x']), int(data['y'])))

    return return_list


def get_visible_range(points):
    max_x = max(points, key=lambda point: point.x).x
    min_x = min(points, key=lambda point: point.x).x
    max_y = max(points, key=lambda point: point.y).y
    min_y = min(points, key=lambda point: point.y).y

    return [Point(min_x, min_y), Point(max_x, max_y)]


class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y


if __name__ == '__main__':
    main()
