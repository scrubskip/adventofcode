package day04

import java.io.File
import kotlin.math.pow

fun main() {
    val input = File("src/day04/Day04.txt").readLines()
    val scratchers = input.map { Scratcher(it) }

    println(scratchers.sumOf { it.getPointValue() })
}

val SPACE = Regex("\\s+")

class Scratcher(input: String) {
    private val winningNumbers: MutableSet<Long> = mutableSetOf()
    private val numbers: MutableSet<Long> = mutableSetOf()
    val id: Int

    init {
        id = input.substring(0, input.indexOf(":")).split(SPACE)[1].toInt()
        val numberStrings = input.substring(input.indexOf(":") + 1).split("|").map { it.trim() }
        winningNumbers.addAll(numberStrings[0].split(SPACE).map { it.toLong() })
        numbers.addAll(numberStrings[1].split(SPACE).map { it.toLong() })
    }

    fun getPointValue(): Long {
        val count = numbers.count { winningNumbers.contains(it) }
        return if (count == 0) {
            0L
        } else {
            2.0.pow(numbers.count { winningNumbers.contains(it) } - 1).toLong()
        }
    }
}