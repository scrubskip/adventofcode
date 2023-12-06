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
            Pair(7, 9),
            Pair(15, 40),
            Pair(30, 200)
        )
        assertEquals(288, getMarginOfError(timeToDistance))
    }
}