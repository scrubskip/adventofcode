package day09

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class Day9KtTest {

    @Test
    fun testExample() {
        val input = listOf(
            "2199943210",
            "3987894921",
            "9856789892",
            "8767896789",
            "9899965678"
        )
        val heightMap = parseData(input)
        assertEquals(5, heightMap.heightData.size)
        assertEquals(10, heightMap.heightData[0].size)

        assertArrayEquals(intArrayOf(2, 1, 9, 9, 9, 4, 3, 2, 1, 0), heightMap.heightData[0])

        val lowPointHeights = heightMap.findLowPointHeights()
        assertEquals(4, lowPointHeights.size)
        assertEquals(15, getRiskFactor(lowPointHeights))


        val lowPoints = heightMap.findLowPoints()
        val largest3 = lowPoints.map { heightMap.findBasinSize(it) }.sortedDescending().take(3)
            .fold(1) { acc, value -> acc * value }
        assertEquals(1134, largest3)
    }

}