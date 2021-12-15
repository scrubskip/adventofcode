package day15

import java.io.File

fun main() {
    val board = Board.getBoard(File("src/day15", "day15input.txt").readLines())
    println("[${board.numRows} x ${board.numCols}]")

    println(board.getLowestPathSum())
}


class Board(private val data: Array<IntArray>) {
    val numCols = data[0].size
    val numRows = data.size
    val cache: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

    fun getLowestPathSum(): Int {
        with(Pair(numRows - 1, numCols - 1)) {
            return getLowestPathSum(this)
        }
    }

    fun getLowestPathSum(cell: Pair<Int, Int>): Int {
        if (cache.containsKey(cell)) {
            return cache[cell]!!
        }

        return if (isStart(cell)) {
            0
        } else {
            getValue(cell) + (getNeighbors(cell).minOf { neighbor ->
                getLowestPathSum(neighbor)
                    .also { cache[neighbor] = it }
            })
        }
    }

    private fun isStart(cell: Pair<Int, Int>): Boolean {
        return cell.first == 0 && cell.second == 0
    }

    fun getNeighbors(cell: Pair<Int, Int>): List<Pair<Int, Int>> {
        // Only build up and to the left
        return buildList {
            val rowIndex = cell.first
            val colIndex = cell.second

            if (rowIndex > 0) add(Pair(rowIndex - 1, colIndex))
            if (colIndex > 0) add(Pair(rowIndex, colIndex - 1))
        }
    }

    fun getValue(cell: Pair<Int, Int>): Int {
        return data[cell.first][cell.second]
    }

    companion object {
        fun getBoard(input: List<String>): Board {
            return Board(
                input.map {
                    it.toCharArray()
                        .map { cell -> cell.digitToInt() }
                        .toIntArray()
                }.toTypedArray()
            )
        }
    }
}

