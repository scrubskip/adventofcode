package day02

import java.io.File

fun main() {
    println(File("src/day02/day2input.txt").readLines().sumOf { calculateScore(it) })
}

fun getWinTable(): Map<String, Int> {
    return mapOf(
        "A X" to 0,
        "A Y" to 1,
        "A Z" to -1,
        "B X" to -1,
        "B Y" to 0,
        "B Z" to 1,
        "C X" to 1,
        "C Y" to -1,
        "C Z" to 0
    )
}

/**
 * Parses "[A,B,C] [X, Y, Z]" into a pair
 */
fun parseMatch(input: String): Pair<String, String> {
    val match = input.split(" ")
    return Pair(match[0], match[1])
}

fun calculateScore(match: String): Int {
    val (opponent, player) = parseMatch(match)
    val playScore = when (player) {
        "X" -> 1
        "Y" -> 2
        "Z" -> 3
        else -> throw Exception("invalid player value $player")
    }
    return playScore + ((getWinTable().getOrDefault(match, 0) + 1) * 3)
}