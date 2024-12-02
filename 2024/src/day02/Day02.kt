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
