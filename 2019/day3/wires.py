#!/usr/bin/env python
#


def main():
    input = open("day3input.txt", "r")
    wire_1 = input.readline().split(",")
    wire_2 = input.readline().split(",")

    coordinates_1 = get_wire_coordinates(wire_1)
    coordinates_2 = get_wire_coordinates(wire_2)

    intersection = get_intersection(coordinates_1, coordinates_2)

    return get_distance(intersection)


def get_wire_coordinates(directions):
    """Returns a sorted list of xy coordinates that are occupied"""
    return_list = []

    x = 0
    y = 0
    for direction in directions:
        dir_str = str.upper(direction[0])
        size = int(direction[1:])
        for _ in range(size):
            if ("U" == dir_str):
                y += 1
            elif ("D" == dir_str):
                y -= 1
            elif ("R" == dir_str):
                x += 1
            elif ("L" == dir_str):
                x -= 1
            return_list.append(tuple([x, y]))

    return_list.sort(key=get_distance)

    return return_list


def get_intersection(coordinates1, coordinates2):
    coordinate_set = set(coordinates1)
    for coordinate in coordinates2:
        if (coordinate in coordinate_set):
            return coordinate
    return None


def get_distance(point):
    return abs(point[0]) + abs(point[1])


if __name__ == "__main__":
    print(main())
