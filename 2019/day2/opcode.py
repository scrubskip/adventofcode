#!/usr/bin/env python
#


def main():
    opcode_data = open("day2input.txt", "r").readline()
    opcode_data = map(int, opcode_data.split(","))

    # Edits from program
    opcode_data[1] = 12
    opcode_data[2] = 2

    opcode_data = run(opcode_data)
    return opcode_data[0]

def run(data):
    program_counter = 0
    
    while (True):
        opcode = data[program_counter]
        if (opcode == 99):
            break
        input_1 = data[data[program_counter + 1]]
        input_2 = data[data[program_counter + 2]]
        output_pos = data[program_counter + 3]
        output = 0

        print [opcode, input_1, input_2, output_pos]

        if (opcode == 1):
            output = input_1 + input_2
        elif (opcode == 2):
            output = input_1 * input_2
        else:
            raise AssertionError("invalid opcode")

        print(data)

        data[output_pos] = output
        program_counter += 4

    return data

if __name__ == "__main__":
    print main()
