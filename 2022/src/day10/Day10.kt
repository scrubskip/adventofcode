package day10

import java.io.File

fun main() {
    val input = File("src/day10/day10input.txt").readLines()
    val cpu = SimpleCpu()
    cpu.executeProgram(input)
    println(cpu.getAccumulatedSignalStrength())
}

class SimpleCpu {
    private var _cycleCounter = 0
    private var _registerX = 1
    private var _accumulatedStrength = 0

    // Map of cycle count to what adjustments should happen at that cycle.
    private val _mapAdjust = mutableMapOf<Int, MutableList<Int>>()

    fun executeProgram(instructions: List<String>) {
        instructions.forEach { doInstruction(it) }
    }

    private fun doInstruction(instruction: String) {
        if (instruction == "noop") {
            incrementCycle()
        } else if (instruction.startsWith("addx")) {
            incrementCycle()
            incrementCycle()
            _registerX += instruction.substring(5).toInt()
        }
    }

    fun incrementCycle() {
        _cycleCounter++
        // println("After Cycle: $_cycleCounter Register X: $_registerX")
        if (_cycleCounter == 20 || (_cycleCounter - 20) % 40 == 0) {
            _accumulatedStrength += getSignalStrength()
        }
    }

    fun getSignalStrength(): Int {
        return _cycleCounter * _registerX
    }

    fun getAccumulatedSignalStrength(): Int {
        return _accumulatedStrength
    }
}