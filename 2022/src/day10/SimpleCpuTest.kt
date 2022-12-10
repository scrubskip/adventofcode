package day10

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SimpleCpuTest {
    @Test
    fun testSample() {
        val input = listOf(
            "noop",
            "addx 3",
            "addx -5"
        )

        val cpu = SimpleCpu()
        cpu.executeProgram(input)
    }

    @Test
    fun testLargerSample() {
        val input = File("src/day10/day10sample.txt").readLines()
        val cpu = SimpleCpu()
        cpu.executeProgram(input)
        assertEquals(13140, cpu.getAccumulatedSignalStrength())
    }

    @Test
    fun testLargerSampleWithDisplay() {
        val input = File("src/day10/day10sample.txt").readLines()
        val display = Display()
        val cpu = SimpleCpu(display)
        cpu.executeProgram(input)
        display.printDisplay()
    }
}