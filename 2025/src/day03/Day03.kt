package day03

import java.io.File

fun main() {
    val input = File("src/day03/day03input.txt").readLines()
    println(input.sumOf { getJoltage(it) })
    println(input.sumOf { getJoltageWithWindow(it, 12) })
}

fun getJoltage(batteries: String): Int {
    // Get the first character
    val firstIndexOfMax = batteries.take(batteries.length - 1).indices
        .maxBy { batteries[it].digitToInt() }
    // println("$batteries $firstIndexOfMax")
    // println(batteries.substring(firstIndexOfMax + 1, batteries.length))
    val restOfBattery = batteries.substring(firstIndexOfMax + 1, batteries.length)
    val nextIndex = restOfBattery.indices
        .maxBy { restOfBattery[it].digitToInt() } + firstIndexOfMax + 1
    // println("$nextIndex")
    return batteries[firstIndexOfMax].digitToInt() * 10 + batteries[nextIndex].digitToInt()
}

fun getJoltageWithWindow(batteries: String, joltageCount: Int): Long {
    var returnString = ""
    var windowString = ""
    var windowStartIndex = 0
    var windowEndIndex = batteries.length - joltageCount + 1

    // Trim off the last
    while (returnString.length < joltageCount) {
        windowString = batteries.substring(windowStartIndex, windowEndIndex)

        println("$windowString")
        val maxIndexInWindow = windowString.indices
            .maxBy { windowString[it].digitToInt() }
        val adjustedIndex = maxIndexInWindow + windowStartIndex

        // println("adding ${batteries[adjustedIndex]} at ${adjustedIndex}")
        windowStartIndex += maxIndexInWindow
        returnString += batteries[windowStartIndex]

        if (windowStartIndex == windowEndIndex) {
            // just take the rest and break
            returnString += batteries.substring(windowStartIndex + 1)
            break
        }

        windowStartIndex++
        windowEndIndex++
    }

    return returnString.toLong()
}

