package day07

import java.io.File
import kotlin.math.abs

fun main() {
    var positions = File("src/day07", "day7input.txt").readLines().single().split(",").map{it.toInt()}
    println(findLowestFuel(positions, ::calculateFuelLinear).first)
}

fun findLowestFuel(positions: List<Int>, calc : (List<Int>, Int) -> Int = ::calculateFuel) : Pair<Int, Int> {
    val positionsSorted = positions.sortedDescending()
    var min : Int = -1
    var minIndex : Int = -1
    for (i in 0..positionsSorted.first()) {
        var fuel = calc(positionsSorted, i)
        if (min == -1 || (fuel < min)) {
            min = fuel
            minIndex = i
        }
    }
    return Pair(min, minIndex)
}

fun calculateFuel(positions : List<Int>, endPosition: Int) : Int {
    return positions.sumOf { abs(it - endPosition) }
}

fun calculateFuelLinear(positions: List<Int>, endPosition: Int) : Int {
    val cost : MutableMap<Int, Int> = mutableMapOf(Pair(1,1))
    return positions.sumOf {
        var distance = abs(it - endPosition)
        cost.getOrPut(distance) {
            (1 .. distance).sum()
        }
    }
}