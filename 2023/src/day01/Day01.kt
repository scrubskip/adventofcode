package day01

import java.io.File

fun main() {
    val input = File("src/day01/Day01.txt").readLines()
    println(input.map { getCalibration(it) }.sum())
}

fun getCalibration(input: String): Int {
    return "${input.first { it.isDigit() }}${input.last { it.isDigit() }}".toInt()
}