package day15

import java.io.File

fun main() {
    val board = Board.getBoard(File("src/day15", "day15input.txt").readLines(), true)
    println("[${board.numRows} x ${board.numCols}]")

    println(board.getLowestPathSum())
}


class Board(private val data: Array<IntArray>, val multiplied: Boolean = false) {
    val numCols = data[0].size
    val numRows = data.size
    val cache: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

    fun getRows(): Int {
        return if (multiplied) {
            numRows * 5
        } else {
            numRows
        }
    }

    fun getCols(): Int {
        return if (multiplied) {
            numCols * 5
        } else {
            numCols
        }
    }

    fun getLowestPathSum(): Int {
        return getLowestPathSum(Pair(getRows() - 1, getCols() - 1))
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
                    .also {
                        if (!cache.containsKey(neighbor) || it < cache[neighbor]!!) {
                            cache[neighbor] = it
                        }
                    }
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
            // if (colIndex < getCols() - 1) add(Pair(rowIndex, colIndex + 1))
            // if (rowIndex < getRows() - 1) add(Pair(rowIndex + 1, colIndex))
        }
    }

    fun getValue(cell: Pair<Int, Int>): Int {
        return if (multiplied) {
            val tileY = cell.first / numRows
            val tileX = cell.second / numCols
            val innerIndexY = cell.first % numRows
            val innerIndexX = cell.second % numCols

            val startValue = data[innerIndexY][innerIndexX]
            val computedValue = (startValue + tileY + tileX)

            if (computedValue <= 9) {
                computedValue
            } else {
                (computedValue) % 9
            }
        } else {
            data[cell.first][cell.second]
        }
    }

    companion object {
        fun getBoard(input: List<String>, multiplied: Boolean = false): Board {
            return Board(
                input.map {
                    it.toCharArray()
                        .map { cell -> cell.digitToInt() }
                        .toIntArray()
                }.toTypedArray(),
                multiplied
            )
        }
    }
}

