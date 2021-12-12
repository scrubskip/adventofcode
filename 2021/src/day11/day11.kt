package day11

import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min

fun main() {
    val octoBoard = parseData(File("src/day11", "day11input.txt").readLines())

    println(getAllFlashStep(octoBoard))
}

fun getFlashCount(octoBoard: OctoBoard, stepCount: Int): Int {
    var sumFlashes = 0
    for (i in 1..stepCount) {
        sumFlashes += octoBoard.runStep()
    }
    return sumFlashes
}

fun getAllFlashStep(octoBoard: OctoBoard): Int {
    var step = 0
    while (!octoBoard.isAllFlash()) {
        octoBoard.runStep()
        step++
    }
    return step
}

fun parseData(input: List<String>): OctoBoard {
    return OctoBoard(input.map { it.toCharArray().map { digit -> digit.digitToInt() }.toIntArray() }.toTypedArray())
}

data class OctoBoard(val boardData: Array<IntArray>) {

    /**
     * Runs a step.
     * @return Number of flashed cells
     */
    fun runStep(): Int {
        var cellsToFlash = ArrayDeque<Pair<Int, Int>>()
        var flashedCells = mutableSetOf<Pair<Int, Int>>()
        // First add 1 to all points
        var numCols: Int = 0
        boardData.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, value ->
                boardData[rowIndex][colIndex] = value + 1
                if (boardData[rowIndex][colIndex] > 9) {
                    cellsToFlash.add(Pair(rowIndex, colIndex))
                }
                if (colIndex > numCols) {
                    numCols = colIndex
                }
            }
        }
        while (cellsToFlash.isNotEmpty()) {
            flashCell(cellsToFlash.removeFirst(), numCols + 1, cellsToFlash, flashedCells)
        }
        // Now take all flashed cells and set to 0
        flashedCells.forEach {
            boardData[it.first][it.second] = 0
        }
        return flashedCells.size
    }

    private fun flashCell(
        cell: Pair<Int, Int>,
        numCols: Int,
        cellsToFlash: ArrayDeque<Pair<Int, Int>>,
        flashedCells: MutableSet<Pair<Int, Int>>
    ) {
        if (flashedCells.contains(cell)) {
            return
        }
        flashedCells.add(cell)
        // Add 1 to all the neighbors
        val neighbors = getNeighbors(cell, boardData.size, numCols)
        for (neighbor in neighbors) {
            boardData[neighbor.first][neighbor.second] += 1
        }
        for (neighbor in neighbors) {
            if (boardData[neighbor.first][neighbor.second] > 9) {
                cellsToFlash.add(neighbor)
            }
        }
    }

    fun isAllFlash(): Boolean {
        return boardData.all { row -> row.all { it == 0 } }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OctoBoard

        if (!boardData.contentDeepEquals(other.boardData)) return false

        return true
    }

    override fun hashCode(): Int {
        return boardData.contentDeepHashCode()
    }

    override fun toString(): String {
        return boardData.map {
            it.joinToString()
        }.joinToString(separator = "\n")
    }
}

fun getNeighbors(cell: Pair<Int, Int>, numRows: Int, numCols: Int): List<Pair<Int, Int>> {
    return buildList {
        for (row in (max(0, cell.first - 1)..min(cell.first + 1, numRows - 1))) {
            for (col in (max(0, cell.second - 1)..min(cell.second + 1, numCols - 1))) {
                if (row != cell.first || col != cell.second) {
                    add(Pair(row, col))
                }
            }
        }
    }
}