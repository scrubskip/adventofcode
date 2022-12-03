package day03

import java.io.File

fun main() {
    val input = File("src/day03/day03input.txt").readLines()
    println(input.sumOf { getPriority(getDuplicateItemType(it)) })

    println(input.chunked(3).sumOf { getPriority(getSharedItem(it)) })
}

/**
 * @return the bad item type.
 */
fun getDuplicateItemType(input: String): Char? {
    // Divide string in half
    val part1 = input.dropLast(input.length / 2)
    val part2 = input.drop(input.length / 2)

    // Find common string
    return part1.find { part2.contains(it) }
}

/**
 * @return the common item between the list of string
 */
fun getSharedItem(input: List<String>): Char? {
    val letterSet = mutableMapOf<Char, Int>()
    var foundChar: Char? = null
    input.map {
        it.toSet()
    }.forEach { set ->
        set.forEach { char ->
            letterSet[char] = letterSet.getOrDefault(char, 0) + 1
            if (letterSet[char] == input.size) {
                foundChar = char
            }
        }
    }
    return foundChar
}

fun getPriority(type: Char?): Int {
    if (type == null) return -1
    return when (type) {
        in 'a'..'z' -> type - 'a' + 1
        in 'A'..'Z' -> type - 'A' + 27
        else -> -1
    }
}