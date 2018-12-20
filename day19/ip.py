from parse import compile


def main():
    pass


class OpcodeExecutor:
    COMMANDS = ["addr", "addi", "mulr", "muli", "banr", "bani", "borr", "bori",
                "setr", "seti", "gtir", "gtri", "gtrr", "eqir", "eqri", "eqrr"]

    def __init__(self):
        self.registers = [0, 0, 0, 0, 0, 0]
        self.ip_register = 0
        self.program = []

    COMMAND_MATCHER = compile("{command} {a:d} {b:d} {c:d}")

    def load_program(self, input_list):
        for instruction in input_list:
            instruction = instruction.strip()
            if (instruction.startswith("#")):
                # set the ip_register
                self.ip_register = int(instruction[3:])
            data = OpcodeExecutor.COMMAND_MATCHER.parse(instruction)
            if (data):
                self.program.append(
                    (data['command'], [data['a'], data['b'], data['c']]))

    def executeCommand(self, command, args, initial=[0, 0, 0, 0, 0, 0]):
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

        # print self.registers
        # print "{0} {1} {2} {3}".format(command, argA, argB, resultIndex)

        self.registers[resultIndex] = resultValue
        return self.registers
