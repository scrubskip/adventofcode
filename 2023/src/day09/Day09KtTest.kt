package day09

import kotlin.test.Test
import kotlin.test.assertEquals

class Day09KtTest {
    @Test
    fun testExample() {
        var input = listOf(0, 3, 6, 9, 12, 15).map { it.toLong() }
        assertEquals(18, getNextInSequence(input))

        input = listOf(1, 3, 6, 10, 15, 21)
        assertEquals(28, getNextInSequence(input))

        input = listOf(10, 13, 16, 21, 30, 45)
        assertEquals(68, getNextInSequence(input))
    }

    @Test
    fun testParse() {
        var input = listOf("0 3 6 9 12 15", "1 3 6 10 15 21", "10 13 16 21 30 45")
        assertEquals(114, getSumOfNext(input))
    }
}