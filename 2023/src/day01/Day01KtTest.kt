package day01

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day01KtTest {
    @Test
    fun testExample() {
        val input = listOf(
            "1abc2",
            "pqr3stu8vwx",
            "a1b2c3d4e5f",
            "treb7uchet"
        )
        val output = input.map { getCalibration(it) }
        assertEquals(12, output[0])
        assertEquals(38, output[1])
        assertEquals(15, output[2])
        assertEquals(77, output[3])
        assertEquals(142, output.sum())
    }

    @Test
    fun testAdjustment() {
        val input = listOf(
            "two1nine",
            "eightwothree",
            "abcone2threexyz",
            "xtwone3four",
            "4nineeightseven2",
            "zoneight234",
            "7pqrstsixteen"
        )
        val output = input.map { getCalibration(getAdjustedString(it)) }
        assertEquals(29, output[0])
        assertEquals(83, output[1])
        assertEquals(13, output[2])
        assertEquals(24, output[3])
        assertEquals(42, output[4])
        assertEquals(14, output[5])
        assertEquals(76, output[6])
        assertEquals(281, output.sum())

    }
}