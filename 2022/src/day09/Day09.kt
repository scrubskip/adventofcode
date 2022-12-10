package day09

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val input = File("src/day09/day09input.txt").readLines()
    val rope = Rope()
    rope.processInput(input)
    println(rope.getVisitedCount())

}

typealias Cell = Pair<Int, Int>

class Rope {
    private var _headPosition: Cell = Cell(0, 0)
    private var _tailPosition: Cell = Cell(0, 0)

    private val _tailPositions = mutableSetOf(_tailPosition)

    fun processInput(input: List<String>) {
        for (s in input) {
            val (direction, count) = s.split(" ")
            moveHead(direction, count.toInt())
        }
    }

    fun moveHead(direction: String, count: Int) {
        val deltaX = when (direction) {
            "L" -> -1
            "R" -> 1
            else -> 0
        }
        val deltaY = when (direction) {
            "U" -> -1
            "D" -> 1
            else -> 0
        }
        (0 until count).forEach { _ ->
            // Adjust the head position
            _headPosition = Cell(_headPosition.first + deltaX, _headPosition.second + deltaY)
            // Check the tail: if it's too far away, adjust it
            val gapX = _tailPosition.first - _headPosition.first
            val gapY = _tailPosition.second - _headPosition.second
            if (gapX.absoluteValue > 1 || gapY.absoluteValue > 1
            ) {
                // Move the tail
                _tailPosition = when (direction) {
                    "L" -> Cell(_headPosition.first + 1, _headPosition.second)
                    "R" -> Cell(_headPosition.first - 1, _headPosition.second)
                    "U" -> Cell(_headPosition.first, _headPosition.second + 1)
                    "D" -> Cell(_headPosition.first, _headPosition.second - 1)
                    else -> throw Exception("invalid direction")
                }
            }
            // Mark the tail
            _tailPositions.add(_tailPosition)
        }
    }

    fun getVisitedCount(): Int {
        return _tailPositions.size
    }

}