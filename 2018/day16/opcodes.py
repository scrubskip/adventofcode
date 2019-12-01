from parse import compile


def main():
    inputs = open("day16-1input.txt", "r")

    num_3 = 0
    num_seen = 0
    code_to_possibilities = {}
    while True:
        test_case = OpcodeTestCase.parse(
            inputs.readline(), inputs.readline(), inputs.readline())
        if test_case is not None:
            possibles = test_case.get_possible_opcodes()
            print "Evaluating ", test_case.command, ", ", possibles
            if (len(possibles) == 1):
                print possibles, " is ", test_case.command
            if (len(possibles) >= 3):
                num_3 += 1
            num_seen += 1
            command = test_case.command[0]
            if (command in code_to_possibilities):
                # based on existing possibles, try to intersect
                for cmd in possibles:
                    if (cmd not in code_to_possibilities[command]):
                        code_to_possibilities[command].append(cmd)
            else:
                code_to_possibilities[command] = possibles
        else:
            break
        if (inputs.readline() == ""):
            break

    print num_3, num_seen
    code_to_op = find_op_codes(code_to_possibilities)
    op_to_code = {}
    for code in code_to_op:
        op_to_code[code] = code_to_op[code]
    print op_to_code
    # now execute the file.
    instructions = open("day16-2input.txt", "r")

    executor = OpcodeExecutor()
    p = compile("{0} {1} {2} {3}")
    for line in instructions:
        data = p.parse(line)
        if not data:
            raise ValueError("Invalid line " + line)
        #print data
        full_command = map(int, [data[0], data[1], data[2], data[3]])

        executor.execute_full_command(full_command, op_to_code)
    print executor.registers


def find_op_codes(code_to_possibilities):
    code_to_op = {}
    while (len(code_to_op) < 16):
        for (key, item) in enumerate(list(code_to_possibilities.viewitems())):
            possibilities = item[1]
            # print key, possibilities
            if (len(possibilities) == 1):
                command = possibilities[0]
                code_to_op[key] = command
                for key2 in code_to_possibilities:
                    if (command in code_to_possibilities[key2]):
                        code_to_possibilities[key2].remove(command)
                if (len(code_to_possibilities[key2]) == 0):
                    code_to_possibilities.pop(key)
    return code_to_op


class OpcodeExecutor:
    COMMANDS = ["addr", "addi", "mulr", "muli", "banr", "bani", "borr", "bori",
                "setr", "seti", "gtir", "gtri", "gtrr", "eqir", "eqri", "eqrr"]

    def __init__(self):
        self.registers = [0, 0, 0, 0]

    def execute_full_command(self, full_command, op_to_code):
        self.executeCommand(op_to_code[full_command[0]], full_command[1:])

    def executeCommand(self, command, args, initial=[0, 0, 0, 0]):
        self.registers = initial
        # Where to store the output
        resultIndex = args[2]
        argB = args[1] if (command.endswith("i")) else self.registers[args[1]]
        resultValue = 0
        if (command.startswith("add")):
            argA = self.registers[args[0]]
            resultValue = argA + argB
        elif (command.startswith("mul")):
            argA = self.registers[args[0]]
            resultValue = argA * argB
        elif (command.startswith("ban")):
            argA = self.registers[args[0]]
            resultValue = argA & argB
        elif (command.startswith("bor")):
            argA = self.registers[args[0]]
            resultValue = argA | argB
        elif (command.startswith("set")):
            argA = args[0] if (command.endswith(
                "i")) else self.registers[args[0]]
            resultValue = argA
        elif (command.startswith("gt")):
            argA = args[0] if (command[2] == (
                "i")) else self.registers[args[0]]
            resultValue = 1 if argA > argB else 0
        elif (command.startswith("eq")):
            argA = args[0] if (command[2] == (
                "i")) else self.registers[args[0]]
            resultValue = 1 if argA == argB else 0
        else:
            raise ValueError("Unknown command " + command)

        #print self.registers
        #print "{0} {1} {2} {3}".format(command, argA, argB, resultIndex)

        self.registers[resultIndex] = resultValue
        return self.registers


class OpcodeTestCase:
    def __init__(self, initial, command, result):
        """Create an opcode test which shows the original state of the 4 registers
        as an array, the command as an array and the result.
        """
        self.initial = initial
        self.command = command
        self.result = result

    BEFORE_MATCHER = compile("Before: [{0}, {1}, {2}, {3}]")
    COMMAND_MATCHER = compile("{0} {1} {2} {3}")
    RESULT_MATCHER = compile("After:  [{0}, {1}, {2}, {3}]")

    def get_possible_opcodes(self):
        executor = OpcodeExecutor()
        args = self.command[1:]
        possibles = []
        for cmd in OpcodeExecutor.COMMANDS:
            output = executor.executeCommand(
                cmd, args, map(None, self.initial))
            if (output == self.result):
                possibles.append(cmd)
        return possibles

    @staticmethod
    def parse(beforeLine, commandLine, resultLine):
        """Parse an opcode test in the form:
                Before: [1, 0, 2, 0]
                4 1 0 1
                After:  [1, 1, 2, 0]
        This will result in initial = [1, 0, 2, 0], command = [4 1 0 1], result = [1, 1, 2, 0]
        """
        beforeData = OpcodeTestCase.BEFORE_MATCHER.parse(beforeLine.strip())
        commandData = OpcodeTestCase.COMMAND_MATCHER.parse(commandLine.strip())
        resultData = OpcodeTestCase.RESULT_MATCHER.parse(resultLine.strip())

        # print "Before: ", beforeData
        # print "Command:", commandData
        # print "Result: ", resultData

        if (beforeData and commandData and resultData):
            before = map(int, [beforeData[0], beforeData[1],
                               beforeData[2], beforeData[3]])
            command = map(int, [commandData[0], commandData[1],
                                commandData[2], commandData[3]])
            result = map(int, [resultData[0], resultData[1],
                               resultData[2], resultData[3]])
            return OpcodeTestCase(before, command, result)

        raise ValueError("Invalid input: " + beforeLine +
                         " " + commandLine + " " + resultLine)


if __name__ == '__main__':
    main()
