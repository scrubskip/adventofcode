package day05


import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min

fun main() {
    val input = File("src/day05", "day5_input.txt").readLines()
    val lines = input.mapNotNull { parseLine(it) }
    val ventMap = VentMap()
    lines.filter { isCardinal(it) }.forEach() {
        ventMap.processLine(it.first, it.second)
    }
    println(ventMap.countPointsWithSize(2))

    val ventMap2 = VentMap()
    lines.forEach() {
        ventMap2.processLine(it.first, it.second)
    }
    println(ventMap2.countPointsWithSize(2))
}

fun parseLine(lineStr : String) : Pair<Pair<Int, Int>, Pair<Int, Int>>? {
    val regex = """(?<startX>\d+),(?<startY>\d+) -> (?<endX>\d+),(?<endY>\d+)""".toRegex()
    val result = regex.matchEntire(lineStr)!!.groups as? MatchNamedGroupCollection
    if (result != null) {
        return Pair(Pair(result["startX"]!!.value.toInt(), result["startY"]!!.value.toInt()),
            Pair(result["endX"]!!.value.toInt(), result["endY"]!!.value.toInt()))
    }
    return null
}

fun isCardinal(arg : Pair<Pair<Int, Int>, Pair<Int, Int>>) : Boolean {
    return arg.first.first == arg.second.first || arg.first.second == arg.second.second
}

class VentMap() {
    // Map of coordinates to count
    private val coordinates : MutableMap<Pair<Int, Int>, Int> = mutableMapOf()

    /**
     * Processes a start and ending point, incrementing the coordinate count
     */
    fun processLine(start: Pair<Int, Int>, end : Pair<Int, Int>) {
        val xDelta = end.first - start.first
        val yDelta = end.second - start.second

        var xStep : Int = 0
        if (xDelta > 0) xStep = 1
        if (xDelta < 0) xStep = -1

        var yStep : Int = 0
        if (yDelta > 0) yStep = 1
        if (yDelta < 0) yStep = -1

        var x = start.first
        var y = start.second

        while (x != end.first + xStep || y != end.second + yStep) {
            coordinates[Pair(x,y)] = coordinates.getOrDefault(Pair(x,y), 0) + 1
            x += xStep
            y += yStep
        }
    }

    fun countPointsWithSize(size : Int) : Int {
        return coordinates.count { it.value >= size }
    }
}