def main():

    value = 10551339

    # for x in xrange(1, value + 1):
    #     if (x % 100 == 0):
    #         print x
    #     if value % x == 0:
    #         sum += x
    # print sum

    print sum_factors(value)


def sum_factors(n):
    total, d = 0, 1
    while d * d < n:
        if n % d == 0:
            total += d + n // d
        d += 1
    if d * d == n:
        total += d
    return total


if __name__ == "__main__":
    main()
