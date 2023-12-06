package day06

import kotlin.test.Test
import kotlin.test.assertEquals

class Day06KtTest {
    @Test
    fun testWinRange() {
        val winRange = getWinRange(7, 9)
        assertEquals(2, winRange.first)
        assertEquals(5, winRange.last)

    }

    @Test
    fun testExample() {
        val timeToDistance = listOf(
            Pair(7L, 9L),
            Pair(15L, 40L),
            Pair(30L, 200L)
        )
        assertEquals(288, getMarginOfError(timeToDistance))
    }

    @Test
    fun testBigWinRange() {
        val winRange = getWinRange(71530, 940200)
        assertEquals(14, winRange.first)
        assertEquals(71516, winRange.last)
    }
}