package day04

import java.io.File

fun main() {
    val input = File("src/day04","/day4input.txt").bufferedReader()

    // Numbers
    val numbers: List<Int> = input.readLine().split(",").map {it.toInt()}
    input.readLine()
    // Now get the boards
    var boards: List<BingoBoard> = input.readLines()
        .windowed(5, 6)
        .map{ BingoBoard.from(it) }

    var result: Pair<Set<Int>, BingoBoard>? = runGame(numbers, boards)
    if (result != null) {
        println(result.second.getUnmarkedValue(result.first) * result.first.last())
    }
    result = runLosingGame(numbers,boards)
    if (result != null) {
        println(result.second.getUnmarkedValue(result.first) * result.first.last())
    }
}

fun runGame(numbers: List<Int>, boards: List<BingoBoard>) : Pair<Set<Int>, BingoBoard>? {
    var numbersSeen : MutableSet<Int> = mutableSetOf()

    for (value in numbers) {
        numbersSeen.add(value)

        for (board in boards) {
            if (board.checkBingo(value, numbersSeen)) {
                return Pair(numbersSeen, board)
            }
        }
    }
    return null
}

fun runLosingGame(numbers: List<Int>, boards: List<BingoBoard>) : Pair<Set<Int>, BingoBoard> {
    var numbersSeen : MutableSet<Int> = mutableSetOf()
    var boardsWon: MutableSet<BingoBoard> = mutableSetOf()

    for (value in numbers) {
        numbersSeen.add(value)

        for (board in boards.filterNot { boardsWon.contains(it) }) {
            if (board.checkBingo(value, numbersSeen)) {
                boardsWon.add(board)
            }
        }
        if (boardsWon.size == boards.size) {
            break
        }
    }

    return Pair(numbersSeen, boardsWon.last())
}
data class BingoBoard(val board: Array<IntArray>) {

    companion object {
        fun from(rows: List<String>) : BingoBoard {
            return BingoBoard(rows.map { row ->
                row.trim().split("\\s+".toRegex()).map {it.toInt()
                }.toIntArray()}.toTypedArray())
        }
    }

    /**
     * Checks a given board for a bingo.
     * Find the number in the board. If it's there, check the row and col against the previously seen value
     * @return true if this board is a bingo
     */
    fun checkBingo(newValue: Int, seenValues: Set<Int>): Boolean {
        for (rowIndex in board.indices) {
            for (colIndex in board[rowIndex].indices) {
                if (board[rowIndex][colIndex] == newValue) {
                    return board[rowIndex].all { value -> seenValues.contains(value) } || board.all { row ->
                        seenValues.contains(
                            row.getOrNull(colIndex)
                        )
                    }
                }
            }
        }
        return false
    }

    fun getUnmarkedValue(seenValues: Set<Int>) : Int {
        return board.fold(0) { acc, ints -> acc + ints.filterNot { element -> seenValues.contains(element) }.sum() }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BingoBoard

        if (!board.contentDeepEquals(other.board)) return false

        return true
    }

    override fun hashCode(): Int {
        return board.contentDeepHashCode()
    }

}