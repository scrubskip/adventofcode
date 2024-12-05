package day03

import java.io.File

fun main() {
    val input = File("src/day03/day03input.txt").readLines()
    println(input.sumOf { getMult(it) })
}

val MULT_REGEX = Regex("mul\\((\\d+),(\\d+)\\)")

fun getMult(input : String) : Int {
    val matchResults = MULT_REGEX.findAll(input)
    return matchResults.map { it.groupValues[1].toInt() * it.groupValues[2].toInt() }.sum()
}