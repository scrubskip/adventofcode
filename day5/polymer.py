#!/usr/bin/env python
#


def main():
    polymerFile = open("day5input.txt", "r")
    polymer = polymerFile.readline()
    polymer = reduce_polymer_recursive(polymer)
    print polymer, ", ", len(polymer)


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
