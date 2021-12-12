package day11

import kotlin.test.*


internal class Day11KtTest {
    @Test
    fun testNeighbors() {
        assertEquals(3, getNeighbors(Pair(0, 0), 10, 10).size)
        assertEquals(5, getNeighbors(Pair(0, 1), 10, 10).size)
        assertEquals(5, getNeighbors(Pair(1, 0), 10, 10).size)
        assertEquals(8, getNeighbors(Pair(1, 1), 10, 10).size)

        assertEquals(5, getNeighbors(Pair(0, 5), 10, 10).size)

        assertEquals(3, getNeighbors(Pair(9, 9), 10, 10).size)
        assertEquals(5, getNeighbors(Pair(9, 8), 10, 10).size)
        assertEquals(5, getNeighbors(Pair(8, 9), 10, 10).size)
        assertEquals(8, getNeighbors(Pair(8, 8), 10, 10).size)
    }

    @Test
    fun testSimpleExample() {
        val input = listOf(
            "11111",
            "19991",
            "19191",
            "19991",
            "11111"
        )
        val octoBoard = parseData(input)
        // println(octoBoard)

        val flashed = octoBoard.runStep()
        // println(octoBoard)
        assertEquals(9, flashed)
    }

    @Test
    fun testAllFlash() {
        val input = listOf(
            "000",
            "001",
            "000"
        )
        assertFalse(parseData(input).isAllFlash())

        assertTrue(parseData(listOf("000", "000", "000")).isAllFlash())
    }

    @Test
    fun testExample() {
        val input = listOf(
            "5483143223",
            "2745854711",
            "5264556173",
            "6141336146",
            "6357385478",
            "4167524645",
            "2176841721",
            "6882881134",
            "4846848554",
            "5283751526"
        )

        val octoBoard = parseData(input)
        // println("$octoBoard\n")
        var sumFlash = 0
        for (step in 1..100) {
            sumFlash += octoBoard.runStep()
            // println("$octoBoard\n")
        }
        assertEquals(1656, sumFlash)

        var step = 100
        while (!octoBoard.isAllFlash()) {
            octoBoard.runStep()
            step++
        }
        assertEquals(195, step)
    }
}