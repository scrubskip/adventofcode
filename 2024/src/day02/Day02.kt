package day02

import readIntsFromString
import java.io.File
import kotlin.math.absoluteValue


fun main() {
    val input = File("src/day02/day02input.txt").readLines()
        .map {
            readIntsFromString(it)
        }

    val output = input.count { list -> isSafe(list) }
    println(output)
    val output2 = input.count { list -> isSafeTolerant(list) }
    println(output2)
}

fun isSafe(input: List<Int>): Boolean {
    var returnValue = true
    var delta = 0
    input.windowed(2) {
        val difference = it[1] - it[0]
        if (delta == 0) {
            delta = if (difference > 0) 1 else -1
        } else {
            if ((delta < 0 && difference > 0) || (delta > 0 && difference < 0)) {
                returnValue = false
            }
        }
        if (difference.absoluteValue !in 1..3) {
            returnValue = false
        }
    }
    return returnValue
}

fun isSafeTolerant(input: List<Int>): Boolean {
    if (isSafe(input)) {
        return true
    }
    // Otherwise, check variations
    for (index in input.indices) {
        if (isSafe(input.filterIndexed { i, _ -> i != index })) {
            return true
        }
    }
    return false
}