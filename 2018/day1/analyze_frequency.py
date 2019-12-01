#!/usr/bin/env python
# 

def main():
    current_frequency = 0
    first_seen = None
    seen_frequencies = set()

    while (first_seen is None):
        frequencies = open("day1input.txt", "r")
        for frequency in frequencies.readlines():
            current_frequency += int(frequency)
            if first_seen is None and current_frequency in seen_frequencies:
                first_seen = current_frequency
            seen_frequencies.add(current_frequency)

    return current_frequency,first_seen

if __name__== "__main__":
  print main()