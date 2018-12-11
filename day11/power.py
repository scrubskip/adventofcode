def main():
    matrix = create_power_matrix(9995)
    sum_matrix = create_sum_matrix(matrix)
    max_value, (max_x, max_y) = get_max(sum_matrix)
    print max_value
    print max_x, max_y

    # print_matrix(sum_matrix)


def create_power_matrix(serial_number):
    matrix = [[0] * 300 for _ in range(300)]

    for i in range(300):
        for j in range(300):
            matrix[i][j] = get_power(i + 1, j + 1, serial_number)

    return matrix


def create_sum_matrix(power_matrix, square_size=3):
    matrix = [[0] * 300 for _ in range(300)]
    for i in range(300 - square_size):
        for j in range(300 - square_size):
            matrix[i][j] = get_square_sum(power_matrix, i, j, square_size)
    return matrix


def get_square_sum(matrix, i, j, square_size):
    sum = 0
    for x in range(i, i + square_size):
        for y in range(j, j + square_size):
            sum += matrix[x][y]
    return sum


# def create_sum_matrix_variable_size(power_matrix):


def print_matrix(matrix):
    for j in range(300):
        row = []
        for i in range(300):
            row.append(matrix[i][j])
        print row


def get_max(sum_matrix):
    max_value = 0
    max_i = -1
    max_j = -1
    for i in range(300 - 3):
        for j in range(300 - 3):
            value = sum_matrix[i][j]
            if value > max_value:
                max_i = i
                max_j = j
                max_value = value
    print "Max: {0} at {1},{2}".format(max_value, max_i, max_j)
    return max_value, (max_i, max_j)


def get_power(x, y, serial_number):
    rack_id = 10 + x
    power_level = rack_id * y
    power_level += serial_number
    power_level *= rack_id
    hundreds_digit = get_hundreds_digit(power_level)
    return hundreds_digit - 5


def get_hundreds_digit(number):
    if (number < 100):
        return 0
    number_str = str(number)
    return int(number_str[len(number_str) - 3])


if __name__ == '__main__':
    main()
