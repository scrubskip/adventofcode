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

        val rope = Rope()

        rope.processInput(input)
        assertEquals(13, rope.getVisitedCount())
    }
}