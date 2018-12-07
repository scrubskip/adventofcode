#!/usr/bin/env python
#

from parse import compile


def main():
    points_file = open("day6input.txt", "r")
    p = compile("{x:d}, {y:d}")
    points = map(p.parse, points_file.readlines())
    #print points
    max_x = max(points, key=lambda point: point['x'])['x']
    min_x = min(points, key=lambda point: point['x'])['x']
    max_y = max(points, key=lambda point: point['y'])['y']
    min_y = min(points, key=lambda point: point['y'])['y']

    print "Range: [{0},{1}] to [{2},{3}]".format(min_x, min_y, max_x, max_y)
    # If any points are on the edges, throw them out.


if __name__ == '__main__':
    main()
