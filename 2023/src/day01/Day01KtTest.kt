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
}