package day15

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class Day15KtTest {
    val TEST_INPUT = listOf(
        "1163751742",
        "1381373672",
        "2136511328",
        "3694931569",
        "7463417111",
        "1319128137",
        "1359912421",
        "3125421639",
        "1293138521",
        "2311944581"
    )

    @Test
    fun testExample() {
        val board = Board.getBoard(TEST_INPUT)

        assertEquals(10, board.numCols)
        assertEquals(10, board.numRows)

        assertEquals(40, board.getLowestPathSum())
    }

    @Test
    fun testNeighbors() {

    }
}