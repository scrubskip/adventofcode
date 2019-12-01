def main():
    file = open("day2input.txt", "r")

    ids = file.readlines()

    for id in ids:
        for other_id in ids:
            common_string = get_common_string(id, other_id)
            if (len(common_string) == len(id) - 1):
                print id, ",", other_id
                return common_string
    return None

def get_common_string(id, other_id):
    if (len(id) != len(other_id)):
        return None
    common_string = ""
    # walk through string and compare. if same, add to common_string.
    for index, character in enumerate(id):
        if (character == other_id[index]):
            common_string += character

    return common_string

if __name__ == "__main__":
    print main()
