package day05

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class Day5KtTest {

    @Test
    fun testParse() {
        val line = parseLine("0,9 -> 5,9")
        assertEquals(Pair(0, 9), line?.first)
        assertEquals(Pair(5,9), line?.second)
    }

    @Test
    fun testExample() {
        val input = listOf(
            "0,9 -> 5,9",
            "8,0 -> 0,8",
            "9,4 -> 3,4",
            "2,2 -> 2,1",
            "7,0 -> 7,4",
            "6,4 -> 2,0",
            "0,9 -> 2,9",
            "3,4 -> 1,4",
            "0,0 -> 8,8",
            "5,5 -> 8,2"
        )
        val lines = input.mapNotNull { parseLine(it) }
        assertEquals(10, lines.size)
        val ventMap = VentMap()
        lines.filter { isCardinal(it) }.forEach() {
            ventMap.processLine(it.first, it.second)
        }
        assertEquals(5, ventMap.countPointsWithSize(2))
    }
}