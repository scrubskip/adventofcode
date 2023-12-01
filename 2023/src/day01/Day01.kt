package day01

import java.io.File

fun main() {
    val input = File("src/day01/Day01.txt").readLines()
    println(input.sumOf { getCalibration(it) })
    println(input.sumOf { getCalibration(getAdjustedString(it)) })
}

fun getCalibration(input: String): Int {
    return "${input.first { it.isDigit() }}${input.last { it.isDigit() }}".toInt()
}

val DIGIT_REGEX = Regex("(one|two|three|four|five|six|seven|eight|nine)")

/**
 * Replace any digits in words with their real number
 */
fun getAdjustedString(input: String): String {
    val match = DIGIT_REGEX.find(input)
    return if (match == null) {
        input
    } else {
        getAdjustedString(
            input.substring(0, match.range.first) +
                    getDigit(match.value) + input.substring(match.range.last)
        )
    }
}

fun getDigit(input: String): String {
    return when (input) {
        "one" -> "1"
        "two" -> "2"
        "three" -> "3"
        "four" -> "4"
        "five" -> "5"
        "six" -> "6"
        "seven" -> "7"
        "eight" -> "8"
        "nine" -> "9"
        else -> throw Exception("Invalid digit $input")
    }
}