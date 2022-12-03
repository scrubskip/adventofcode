package day03

import java.io.File

fun main() {
    val input = File("src/day03/day03input.txt").readLines()
    println(input.sumOf { getPriority(getItemType(it)) })
}

fun getItemType(input: String): Char? {
    // Divide string in half
    val part1 = input.dropLast(input.length / 2)
    val part2 = input.drop(input.length / 2)

    // Find common string
    return part1.find { part2.contains(it) }
}

fun getPriority(type: Char?): Int {
    if (type == null) return -1
    return when (type) {
        in 'a'..'z' -> type - 'a' + 1
        in 'A'..'Z' -> type - 'A' + 27
        else -> -1
    }
}