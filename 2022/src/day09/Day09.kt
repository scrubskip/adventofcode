package day09

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val input = File("src/day09/day09input.txt").readLines()
    val rope = Rope(2)
    rope.processInput(input)
    println(rope.getVisitedCount())

    val longRope = Rope(10)
    longRope.processInput(input)
    println(longRope.getVisitedCount())
}

typealias Cell = Pair<Int, Int>

class Rope {
    private val _segments: MutableList<Cell>
    private val _seenPositions = mutableSetOf(Cell(0, 0))

    constructor(segmentCount: Int) {
        _segments = MutableList(segmentCount) { Cell(0, 0) }
    }

    fun processInput(input: List<String>) {
        for (s in input) {
            val (direction, count) = s.split(" ")
            move(direction, count.toInt())
        }
    }

    fun move(direction: String, count: Int) {
        val deltaX = when (direction) {
            "L" -> -1
            "R" -> 1
            else -> 0
        }
        val deltaY = when (direction) {
            "U" -> 1
            "D" -> -1
            else -> 0
        }
        (0 until count).forEach { _ ->
            _segments[0] = Cell(_segments[0].first + deltaX, _segments[0].second + deltaY)
            for (index in 0.._segments.size - 2) {
                moveSegmentIfNecessary(_segments[index], _segments[index + 1], index + 1)
            }
        }
    }

    private fun moveSegmentIfNecessary(segment: Cell, nextSegment: Cell, index: Int) {
        val gapX = nextSegment.first - segment.first
        val gapY = nextSegment.second - segment.second
        if (gapX.absoluteValue > 1 || gapY.absoluteValue > 1) {
            // Move the next segment
            if (gapX == 0 || gapY.absoluteValue > gapX.absoluteValue) {
                // We are on the same X or should be on the same X
                _segments[index] = Cell(segment.first, segment.second + (if (gapY > 0) 1 else -1))
            } else if (gapY == 0 || gapX.absoluteValue > gapY.absoluteValue) {
                // We are on the same Y or should be on the same Y
                _segments[index] = Cell(segment.first + (if (gapX > 0) 1 else -1), segment.second)
            } else {
                // We are diagonal and have to move diagonal towards
                _segments[index] =
                    Cell(segment.first + (if (gapX > 0) 1 else -1), segment.second + (if (gapY > 0) 1 else -1))
            }
        }
        if (index == _segments.size - 1) {
            _seenPositions.add(_segments[index])
        }
    }

    fun getVisitedCount(): Int {
        // println(_seenPositions)
        return _seenPositions.size
    }


}