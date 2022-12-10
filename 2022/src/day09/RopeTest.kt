package day09

import kotlin.test.Test
import kotlin.test.assertEquals

internal class RopeTest {
    @Test
    fun testSample() {
        val input = listOf(
            "R 4",
            "U 4",
            "L 3",
            "D 1",
            "R 4",
            "D 1",
            "L 5",
            "R 2"
        )

        val rope = Rope(2)
        rope.processInput(input)
        assertEquals(13, rope.getVisitedCount())

        val largeRope = Rope(10)
        largeRope.processInput(input)
        assertEquals(1, largeRope.getVisitedCount())
    }

    @Test
    fun testMultiSegment() {
        val input = listOf(
            "R 5",
            "U 8",
            "L 8",
            "D 3",
            "R 17",
            "D 10",
            "L 25",
            "U 20"
        )
        val rope = Rope(10)
        rope.processInput(input)
        assertEquals(36, rope.getVisitedCount())
    }
}