#!/usr/bin/env python
#


def main():
    input = open("day3input.txt", "r")
    wire_1 = input.readline().split(",")
    wire_2 = input.readline().split(",")

    #coordinates_1 = get_wire_coordinates(wire_1)
    #coordinates_2 = get_wire_coordinates(wire_2)

    #intersection = get_intersection(coordinates_1, coordinates_2)

    #return get_distance(intersection)

    map1 = get_wire_distance_map(wire_1)
    map2 = get_wire_distance_map(wire_2)
    return get_intersection_from_maps(map1, map2)


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


def get_wire_distance_map(directions):
    """Return a dict of tuple to distance"""
    return_dict = {}

    x = 0
    y = 0
    distance = 0
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
            distance += 1

            point = tuple([x, y])
            if point not in return_dict or return_dict[point] > distance:
                return_dict[tuple([x, y])] = distance

    return return_dict


def get_intersection(coordinates1, coordinates2):
    coordinate_set = set(coordinates1)
    for coordinate in coordinates2:
        if (coordinate in coordinate_set):
            return coordinate
    return None


def get_intersection_from_maps(map1, map2, should_print=False):
    lowest_distance_sum = None
    lowest_point = None

    for point in map1.keys():
        if point in map2:
            distance_sum = map1[point] + map2[point]
            if should_print:
                print("Found", distance_sum, ",", point, ",",
                      lowest_distance_sum)
            if lowest_distance_sum is None or distance_sum < lowest_distance_sum:
                lowest_distance_sum = distance_sum
                lowest_point = point

    return lowest_distance_sum, lowest_point


def get_distance(point):
    return abs(point[0]) + abs(point[1])


if __name__ == "__main__":
    print(main())
