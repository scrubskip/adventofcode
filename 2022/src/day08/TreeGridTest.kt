package day08

import kotlin.test.Test
import kotlin.test.assertEquals

internal class TreeGridTest {
    @Test
    fun testSample() {
        val input = listOf(
            "30373",
            "25512",
            "65332",
            "33549",
            "35390"
        )

        val grid = TreeGrid(input)
        assertEquals(5, grid.getWidth())
        assertEquals(5, grid.getHeight())
        assertEquals(3, grid.getValue(Cell(0, 0)))
        assertEquals(0, grid.getValue(Cell(1, 0)))
        assertEquals(2, grid.getValue(Cell(0, 1)))

        assertEquals(21, grid.getVisibleCells().size)

        assertEquals(4, grid.getScenicScore(Cell(2, 1)))
        assertEquals(8, grid.getScenicScore(Cell(2, 3)))

        assertEquals(8, grid.getMaxScenicScore())
    }
}