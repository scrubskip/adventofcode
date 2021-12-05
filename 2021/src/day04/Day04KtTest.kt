package day04

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class Day04KtTest {

    fun getTestBoards(): List<BingoBoard> {
        val board1 = BingoBoard.from(listOf(
            "22 13 17 11 0",
            "8 2 23 4 24",
            "21 9 14 16 7",
            "6 10 3 18 5",
            "1 12 20 15 19"))

        val board2 = BingoBoard.from(listOf(
            " 3 15 0 2 22",
            "9 18 13 17 5",
            "19 8 7 25 23",
            "20 11 10 24 4",
            "14 21 16 12 6"))

        val board3 = BingoBoard.from(listOf(
            "14 21 17 24 4",
            "10 16 15 9 19",
            "18 8 23 26 20",
            "22 11 13 6 5",
            "2  0 12 3 7"))

        return listOf(board1, board2, board3)
    }

    fun getNumbers(): List<Int> {
        return listOf(7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1)
    }

    @Test
    fun testExample() {
        val boards: List<BingoBoard> = getTestBoards()

        assertTrue(boards[0].checkBingo(3, setOf(6, 10, 18, 5, 3)), "Row check failed")
        assertTrue(boards[1].checkBingo(13, setOf(0, 7, 10, 16, 13)), "Col check failed")

        assertEquals(188, boards[2].getUnmarkedValue(setOf(7, 4, 9, 5 ,11, 17, 23, 2, 0, 14, 21, 24)), "Unmarked value failed")

        var result = runGame(getNumbers(), boards)
        assertNotNull(result)

        assertEquals(188, result.second.getUnmarkedValue(result.first), "Run game failed")
    }

    @Test
    fun testLosingGame() {
        val result = runLosingGame(getNumbers(), getTestBoards())
        assertEquals(13, result.first.last())
        assertEquals(148, result.second.getUnmarkedValue(result.first))
    }

}