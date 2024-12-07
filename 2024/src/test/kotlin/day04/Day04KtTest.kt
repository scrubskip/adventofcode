package day04

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

class Day04KtTest {

    @Test
    fun countStringInGrid() {
        val grid = """
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX
        """.trimIndent().lines()

        assertEquals(18, countStringInGrid(grid, "XMAS"))
    }
}