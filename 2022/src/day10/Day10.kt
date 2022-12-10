package day10

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val input = File("src/day10/day10input.txt").readLines()
    val display = Display()
    val cpu = SimpleCpu(display)
    cpu.executeProgram(input)
    println(cpu.getAccumulatedSignalStrength())
    display.printDisplay()
}

class Display {
    private var currentXIndex = 0
    private var currentYIndex = 0
    private var display = Array(6) { Array(40) { false } }

    fun writePixel(spritePos: Int) {
        display[currentYIndex][currentXIndex] = (currentXIndex - spritePos).absoluteValue <= 1
        currentXIndex++
        if (currentXIndex % 40 == 0) {
            currentXIndex = 0
            currentYIndex++
        }
    }

    fun printDisplay() {
        display.forEach {
            println(it.joinToString(separator = "") { pixel -> if (pixel) "#" else "." })
        }
    }
}

class SimpleCpu {
    private var _cycleCounter = 0
    private var _registerX = 1
    private var _accumulatedStrength = 0
    private val _display: Display?

    constructor(display: Display? = null) {
        _display = display
    }

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

    private fun incrementCycle() {
        _display?.writePixel(_registerX)
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