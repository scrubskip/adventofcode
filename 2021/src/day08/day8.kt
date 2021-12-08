package day08

import java.io.File

fun main() {
    val input = File("src/day08", "day8input.txt").readLines().map { parseLine(it) }.map { it.second }
    println(getUniqueSegments(input).size)
}

/**
 * Input a line of input signal and output separated by |
 * Output a pair with the input signals in one list and the output signals in 1 list
 */
fun parseLine(input: String): Pair<List<String>, List<String>> {
    val inputList = input.split("|").map { it.trim() }
    return Pair(inputList[0].split(" "), inputList[1].split(" "))
}

fun getUniqueSegments(segments: List<List<String>>): List<String> {
    return segments.flatten().filter { isUniqueSegments(it) }
}

fun isUniqueSegments(segment: String): Boolean {
    return when (segment.length) {
        2 -> true
        3 -> true
        4 -> true
        7 -> true
        else -> false
    }
}