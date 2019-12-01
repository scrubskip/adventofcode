#!/usr/bin/env python
#


def main():
    modules = open("day1input.txt", "r")
    requirement_sum = 0
    for module in modules.readlines():
        requirement_sum += get_fuel_requirement(module)

    return requirement_sum


def get_fuel_requirement(mass):
    return int(int(mass) / 3) - 2


if __name__ == "__main__":
    print main()
