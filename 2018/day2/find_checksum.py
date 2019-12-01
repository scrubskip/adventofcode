def main():
    file = open("day2input.txt", "r")

    two_count = 0
    three_count = 0

    for id in file:
        freq_count = get_frequency_count(id)
        # print id, " ", freq_count
        if (is_count(freq_count, 2)):
            two_count += 1
        if (is_count(freq_count, 3)):
            three_count += 1
    print (two_count * three_count)
    return two_count * three_count

'''Returns a dict with number to integer
'''
def get_frequency_count(id):
    count = {}
    for character in id:
        if character not in count:
            count[character] = 0
        count[character] += 1
    return count

def is_count(freq_count, value):
    for count_value in freq_count.values():
        if count_value == value:
            return True
    return False

if __name__ == "__main__":
    main()
