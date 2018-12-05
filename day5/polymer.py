#!/usr/bin/env python
#

import string


def main():
    polymerFile = open("day5input.txt", "r")
    polymer = polymerFile.readline()
    # polymer = reduce_polymer_recursive(polymer)
    # print polymer, ", ", len(polymer)
    reduced_counts = dict(zip(string.ascii_lowercase[:26], map(lambda x: len(remove_and_reduce(
        polymer, x)), string.ascii_lowercase[:26])))
    print reduced_counts
    print reduced_counts[min(reduced_counts, key=lambda x: reduced_counts[x])]


def remove_and_reduce(polymer, character):
    print "reducing ", character
    removed_polymer = filter(lambda x: x != character and x != character.upper(),
                             polymer)
    return reduce_polymer_recursive(removed_polymer)


def reduce_polymer_recursive(polymer):
    reduced = True

    while reduced:
        polymer, reduced = reduce_polymer(polymer)

    return polymer


def reduce_polymer(polymer):
    new_chars = []
    index = 0
    reduced = False
    while index < len(polymer):
        character = polymer[index]
        next_character = polymer[index +
                                 1] if index < len(polymer) - 1 else None
        if (character.isupper() and character.lower() == next_character) or (character.islower() and character.upper() == next_character):
            # skip
            index += 2
            reduced = True
        else:
            new_chars.append(character)
            index += 1

    return "".join(new_chars), reduced


if __name__ == "__main__":
    main()
