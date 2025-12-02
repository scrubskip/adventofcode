package day01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day01KtTest {

    @Test
    fun testExample() {
        val list1 = listOf(3, 4, 2, 1, 3, 3)
        val list2 = listOf(4, 3, 5, 3, 9, 3)

        assertEquals(11, getDistance(list1, list2))
    }

    @Test
    fun testSimilarity() {
        val list1 = listOf(3, 4, 2, 1, 3, 3)
        val list2 = listOf(4, 3, 5, 3, 9, 3)

        assertEquals(31, getSimilarityScore(list1, list2))
    }
}