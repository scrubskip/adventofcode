from parse import compile


def main():
    executor = OpcodeExecutor()
    executor.load_program(open("day21input.txt", "r"))
    print "IP register ", executor.ip_register, " num commands: ", len(
        executor.program)
    # executor.registers[0] = 1024276
    executor.run_program(write_out=False)


class OpcodeExecutor:
    COMMANDS = ["addr", "addi", "mulr", "muli", "banr", "bani", "borr", "bori",
                "setr", "seti", "gtir", "gtri", "gtrr", "eqir", "eqri", "eqrr"]

    def __init__(self):
        self.registers = [0, 0, 0, 0, 0, 0]
        self.ip_register = 0
        self.ip_value = 0
        self.program = []
        self.seen_five = []
        self.seen_five_set = set()

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

    def run_program(self, write_out=True):
        execution_count = 0
        while (self.ip_value >= 0 and self.ip_value < len(self.program)):
            # if execution_count % 1000 == 0:
                #print "count ", execution_count, ", ", len(self.seen_five)
            self.do_next_instruction(write_out)
            execution_count += 1

        return execution_count

    def do_next_instruction(self, write_out):
        self.registers[self.ip_register] = self.ip_value
        instruction = self.program[self.ip_value]
        old_data = list(self.registers)

        if (self.ip_value == 28):
            # print "Comparing {0} to {1} ({2}) {3}".format(
            #    self.registers[0], bin(self.registers[5]), self.registers[5], len(self.seen_five))
            if (self.registers[5] in self.seen_five_set):
                print "Already seen ", self.registers[5], ", last = ", self.seen_five[len(
                    self.seen_five) - 1]
                exit()

            self.seen_five.append(self.registers[5])
            self.seen_five_set.add(self.registers[5])

        self.executeCommand(instruction[0], instruction[1])

        if (write_out):
            print "ip={0} {1} {2} {3} {4}".format(
                self.ip_value, old_data, instruction[0], instruction[1], self.registers)

        if self.registers[5] != old_data[5]:
            pass
            # print "Register 5 changed: {0} to {1}".format(
            #    old_data[5], self.registers[5])

        self.ip_value = self.registers[self.ip_register]
        self.ip_value += 1

    def executeCommand(self, command, args):
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


if __name__ == "__main__":
    main()
