package day06

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class Day6KtTest {
    @Test
    fun testExample() {
        val input = listOf(3,4,3,1,2)
        var fish = runStep(input)
        assertEquals(listOf(2,3,2,0,1), fish)
        fish = runStep(fish)
        assertEquals(listOf(1,2,1,6,0,8), fish)
        for (i in 3..18) {
            fish = runStep(fish)
        }
        assertEquals(listOf(6,0,6,4,5,6,0,1,1,2,6,0,1,1,1,2,2,3,3,4,6,7,8,8,8,8), fish)
        assertEquals(26, fish.size)
        for (i in 19..80) {
            fish = runStep(fish)
        }
        assertEquals(5934, fish.size)
    }

    @Test
    fun testRunList() {
        val input = listOf(3,4,3,1,2)
        assertEquals(5, runList(input, 0))
        assertEquals(6, runList(input, 2))
        assertEquals(26, runList(input, 18))
        assertEquals(5934, runList(input, 80))
        assertEquals(26984457539, runList(input, 256))
    }

}