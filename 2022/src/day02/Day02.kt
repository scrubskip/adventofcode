package day02

import java.io.File

fun main() {
    val input = File("src/day02/day2input.txt").readLines()
    println(input.sumOf { calculateScore(it) })
    println(input.sumOf { calculateSecretScore(it) })
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

fun getMatchTable(): Map<String, String> {
    return mapOf(
        "A X" to "Z",
        "A Y" to "X",
        "A Z" to "Y",
        "B X" to "X",
        "B Y" to "Y",
        "B Z" to "Z",
        "C X" to "Y",
        "C Y" to "Z",
        "C Z" to "X"
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
    return calculatePlayerScore(match, player)
}

fun calculateSecretScore(match: String): Int {
    val (opponent, outcome) = parseMatch(match)
    val player = getMatchTable().getOrDefault(match, "")
    return calculatePlayerScore("$opponent $player", player);
}

fun calculatePlayerScore(match: String, player: String): Int {
    val playScore = when (player) {
        "X" -> 1
        "Y" -> 2
        "Z" -> 3
        else -> throw Exception("invalid player value $player")
    }
    return playScore + ((getWinTable().getOrDefault(match, 0) + 1) * 3)
}
