package day04

import java.io.File
import kotlin.math.pow

fun main() {
    val input = File("src/day04/Day04.txt").readLines()
    val scratchers = Pile(input)

    println(scratchers.getPointValue())
    println(scratchers.getCardCount())
}

val SPACE = Regex("\\s+")

class Pile(input: List<String>) {
    private val cards: Map<Int, Scratcher>

    init {
        cards = buildMap {
            input.forEach {
                val scratcher = Scratcher(it)
                put(scratcher.id, scratcher)
            }
        }
    }

    fun getCardCount(): Int {
        val countMap = mutableMapOf<Int, Int>()

        cards.values.forEach {
            addScratcher(it, countMap)
        }
        return countMap.values.sum()
    }

    fun getPointValue(): Long {
        return cards.values.sumOf { it.getPointValue() }
    }

    private fun addScratcher(scratcher: Scratcher, countMap: MutableMap<Int, Int>) {
        val id = scratcher.id
        // Add to map
        // println("adding $id")

        countMap.putIfAbsent(id, 0)
        countMap[id] = countMap[id]!! + 1
        (id + 1).rangeTo(id + scratcher.getWinCount()).forEach {
            cards[it]?.let { newScratcher ->
                addScratcher(newScratcher, countMap)
            }
        }
    }
}

class Scratcher(input: String) {
    private val winningNumbers: MutableSet<Long> = mutableSetOf()
    private val numbers: MutableSet<Long> = mutableSetOf()
    private var pointValue: Long? = null
    val id: Int


    init {
        id = input.substring(0, input.indexOf(":")).split(SPACE)[1].toInt()
        val numberStrings = input.substring(input.indexOf(":") + 1).split("|").map { it.trim() }
        winningNumbers.addAll(numberStrings[0].split(SPACE).map { it.toLong() })
        numbers.addAll(numberStrings[1].split(SPACE).map { it.toLong() })
    }

    fun getWinCount(): Int {
        return numbers.count { winningNumbers.contains(it) }
    }

    fun getPointValue(): Long {
        if (pointValue == null) {
            val count = getWinCount()
            pointValue = if (count == 0) {
                0L
            } else {
                2.0.pow(count - 1).toLong()
            }
        }
        return pointValue as Long
    }
}