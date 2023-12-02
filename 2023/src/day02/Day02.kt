package day02

import java.io.File

fun main() {
    val input = File("src/day02/Day02.txt").readLines()
    val games = input.map { Game(it) }

    val colorMap = mapOf<String, Int>(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )

    println(games.filter { it.isValidGame(colorMap) }.sumOf { it.id })
}

class Game {
    private val maxSeen = mutableMapOf<String, Int>()
    val id: Int

    constructor(input: String) {
        id = input.substring(5, input.indexOf(":")).toInt()
        // Divide up reveals
        val reveals = input.substring(input.indexOf(":") + 1).split(";").map { it.trim() }
        reveals.forEach {
            val colorStrings = it.split(",").map { it2: String -> it2.trim() }
            colorStrings.forEach { colorString: String ->
                val pairString = colorString.split(" ")
                val count = pairString[0].toInt()
                val color = pairString[1]
                if (count > maxSeen.getOrDefault(color, 0)) {
                    maxSeen[color] = count
                }
            }
        }
    }

    /**
     * Returns {@code true} if the game could be played based on reveals
     */
    fun isValidGame(colorCount: Map<String, Int>): Boolean {
        return colorCount.filter {
            maxSeen.getOrDefault(it.key, 0) > it.value
        }.isEmpty()
    }

    fun getMaxSeen(color: String): Int {
        return maxSeen.getOrDefault(color, 0)
    }
}