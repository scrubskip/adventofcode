#!/usr/bin/env python
#


def main():
    modules = open("day1input.txt", "r")
    requirement_sum = 0
    for module in modules.readlines():
        requirement_sum += get_fuel_requirement_inclusive(module)

    return requirement_sum


def get_fuel_requirement(mass):
    return int(int(mass) / 3) - 2


def get_fuel_requirement_inclusive(mass):
    total = 0
    fuel = get_fuel_requirement(mass)
    while (fuel > 0):
        total += fuel
        fuel = get_fuel_requirement(fuel)
    return total


if __name__ == "__main__":
    print(main())
