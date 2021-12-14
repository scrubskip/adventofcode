package day13

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class Day13KtTest {
    @Test
    fun testExample() {
        val input = listOf(
            "6,10",
            "0,14",
            "9,10",
            "0,3",
            "10,4",
            "4,11",
            "6,0",
            "6,12",
            "4,1",
            "0,13",
            "10,12",
            "3,4",
            "3,0",
            "8,4",
            "1,10",
            "2,14",
            "8,10",
            "9,0",
            "",
            "fold along y=7",
            "fold along x=5"
        )

        val data = parseInput(input)
        assertEquals(18, data.first.size)
        assertEquals(2, data.second.size)

        assertEquals(Pair(6, 10), data.first.first())
        assertEquals(Pair('y', 7), data.second.first())
        assertEquals(Pair('x', 5), data.second[1])

        //printOutput(data.first)

        val foldOne = foldAlong(data.first, 'y', 7)
        assertEquals(17, foldOne.size)

        val foldTwo = foldAlong(foldOne, 'x', 5)
        assertEquals(16, foldTwo.size)
        //printOutput(foldTwo)
    }
}