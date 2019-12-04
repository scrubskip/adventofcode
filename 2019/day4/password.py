#!/usr/bin/env python
#

from collections import Counter


def main():
    return len(filter(is_valid_password_max_2, range(235741, 706948)))


def is_valid_password(password):
    pass_str = str(password)
    if len(pass_str) != 6:
        return False
    found_double = False
    last_digit = None
    for digit_str in pass_str:
        digit = int(digit_str)
        if digit == last_digit:
            found_double = True
        if digit < last_digit:
            return False
        last_digit = digit

    return found_double


def is_valid_password_max_2(password):
    pass_str = str(password)
    digit_counter = Counter()
    if len(pass_str) != 6:
        return False
    last_digit = None
    for digit_str in pass_str:
        digit = int(digit_str)
        digit_counter[digit] += 1
        if (digit < last_digit):
            return False
        last_digit = digit

    # Search counter for at least 1 2.
    # print(digit_counter)
    found_double = False
    for element in digit_counter.most_common():
        if element[1] == 2:
            found_double = True

    return found_double


if __name__ == "__main__":
    print(main())
