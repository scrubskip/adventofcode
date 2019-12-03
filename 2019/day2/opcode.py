#!/usr/bin/env python
#


def main():
    opcode_data = open("day2input.txt", "r").readline()
    opcode_data = map(int, opcode_data.split(","))

    #opcode_data = run(opcode_data, noun = 12, verb = 2)
    #return opcode_data[0]


    for noun_arg in range(100):
        for verb_arg in range(100):
            test_data = run(opcode_data, noun = noun_arg, verb = verb_arg)
            if (test_data[0] == 19690720):
                print "Found at ", noun_arg, ", ", verb_arg
                return

    print "Not found"

def run(data, *args, **kwargs):
    program_counter = 0
    data = list(data)

    noun = kwargs.get("noun", None)
    verb = kwargs.get("verb", None)

    if (noun):
        data[1] = noun
    if (verb):
        data[2] = verb
    
    while (True):
        opcode = data[program_counter]
        if (opcode == 99):
            break
        input_1 = data[data[program_counter + 1]]
        input_2 = data[data[program_counter + 2]]
        output_pos = data[program_counter + 3]
        output = 0

        #print [opcode, input_1, input_2, output_pos]

        if (opcode == 1):
            output = input_1 + input_2
        elif (opcode == 2):
            output = input_1 * input_2
        else:
            raise AssertionError("invalid opcode")

        #print(data)

        data[output_pos] = output
        program_counter += 4

    return data

if __name__ == "__main__":
    print main()
