package day08

import java.io.File

fun main() {
    val input = File("src/day08/day08input.txt").readLines()
    val grid = TreeGrid(input)
    println(grid.getVisibleCells().size)
}

typealias Cell = Pair<Int, Int>

class TreeGrid {
    private var _values: List<List<Int>>
    private var _width: Int
    private var _height: Int

    constructor(input: List<String>) {
        _values = input.map { it.toCharArray().map { char -> char.toString().toInt() } }
        _width = _values[0].size
        _height = _values.size
    }

    fun getVisibleCells(): List<Cell> {
        val returnSet = mutableSetOf<Cell>()

        // Go left to right
        for (x in 0 until _width) {
            addVisibleCells(Cell(x, 0), 0, 1, returnSet)
            addVisibleCells(Cell(x, _height - 1), 0, -1, returnSet)
        }

        // Go top to bottom
        for (y in 0 until _height) {
            addVisibleCells(Cell(0, y), 1, 0, returnSet)
            addVisibleCells(Cell(_width - 1, y), -1, 0, returnSet)
        }

        return returnSet.toList()
    }

    fun getValue(cell: Cell): Int {
        return _values[cell.second][cell.first]
    }

    fun getWidth(): Int {
        return _width
    }

    fun getHeight(): Int {
        return _height
    }

    /**
     * Adds all the visible cells in a direction starting at an edge cell.
     */
    private fun addVisibleCells(cellStart: Cell, deltaX: Int, deltaY: Int, set: MutableSet<Cell>) {
        set.add(cellStart)
        var currentCell = cellStart
        var candidateCell: Cell
        var lastMaxValue = getValue(cellStart)
        while (inbounds(currentCell)) {
            candidateCell = Cell(currentCell.first + deltaX, currentCell.second + deltaY)
            if (inbounds(candidateCell) && getValue(candidateCell) > lastMaxValue) {
                set.add(candidateCell)
                lastMaxValue = getValue(candidateCell)
            }
            currentCell = candidateCell
        }
    }

    private fun inbounds(cell: Cell): Boolean {
        return cell.first >= 0 && cell.second >= 0 && cell.first <= _width - 1 && cell.second <= _height - 1

    }

    private fun isEdge(cell: Cell): Boolean {
        return cell.first == 0 || cell.second == 0 || cell.first == _width - 1 || cell.second == _height - 1
    }
}