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

        assertEquals(10, board.getCols())
        assertEquals(10, board.getRows())

        assertEquals(40, board.getLowestPathSum())
    }

    @Test
    fun testMultiplied() {
        val board = Board.getBoard(TEST_INPUT, true)
        assertEquals(50, board.getCols())
        assertEquals(50, board.getRows())

        assertEquals(1, board.getValue(Pair(0, 0)))
        assertEquals(2, board.getValue(Pair(10, 0)))
        assertEquals(3, board.getValue(Pair(12, 0)))
        assertEquals(7, board.getValue(Pair(0, 12)))
        assertEquals(3, board.getValue(Pair(10, 10)))

        assertEquals(9, board.getValue(Pair(3, 2)))
        assertEquals(1, board.getValue(Pair(3, 12)))
        assertEquals(2, board.getValue(Pair(13, 12)))
        assertEquals(3, board.getValue(Pair(13, 22)))
        assertEquals(4, board.getValue(Pair(23, 22)))

        assertEquals(315, board.getLowestPathSum())
    }

    @Test
    fun testNeighbors() {

    }
}