package day03

import java.io.File
import kotlin.collections.mutableMapOf

fun main() {
    // testDay3Sample()
    readInput("day3.txt")
}

fun testDay3Sample() {
    val input =
            listOf(
                    "00100",
                    "11110",
                    "10110",
                    "10111",
                    "10101",
                    "01111",
                    "00111",
                    "11100",
                    "10000",
                    "11001",
                    "00010",
                    "01010"
            )
    printStats(input)
}

fun readInput(arg: String) {
    printStats(File("src/day03", arg).readLines())
}

fun printStats(input: List<String>) {
    var (gamma, epsilon) = calulatePowerConsumption(input)
    val powerConsumption = gamma * epsilon
    val o2rating = getO2rating(input)
    val co2rating = getCO2rating(input)
    val lifeSupport = o2rating * co2rating

    println("power $powerConsumption o2 $o2rating co2 $co2rating lifeSupport $lifeSupport")
}

fun calulatePowerConsumption(input: List<String>): Pair<Int, Int> {

    // Map of index to number of "1"s in the input.
    var onesFrequencyMap: MutableMap<Int, Int> = mutableMapOf()
    var maxSize: Int = 0

    input.forEach {
        maxSize = Math.max(it.length, maxSize)
        it.forEachIndexed { index, value ->
            if ("1".single().equals(value)) {
                onesFrequencyMap[index] = onesFrequencyMap.getOrDefault(index, 0) + 1
            }
        }
    }

    // Now loop through the entries in the map to calculate gamma and epsilon
    var gammaString: String = ""
    var epsilonString: String = ""
    val commonSize = (input.size / 2)
    for (index in 0..maxSize - 1) {
        if (onesFrequencyMap.getOrDefault(index, 0) >= commonSize) {
            gammaString += "1"
            epsilonString += "0"
        } else {
            gammaString += "0"
            epsilonString += "1"
        }
    }
    println("gamma $gammaString epsilon $epsilonString")

    return Pair(gammaString.toInt(2), epsilonString.toInt(2))
}

fun getO2rating(input: List<String>): Int {
    return getFilteredListNumber(input, true)
}

fun getCO2rating(input: List<String>): Int {
    return getFilteredListNumber(input, false)
}

fun getFilteredListNumber(input: List<String>, takeGreater: Boolean = false): Int {
    var index: Int = 0
    var currentList: List<String> = input

    while (currentList.size > 1) {
        val pairs = splitList(index, currentList)
        currentList =
                if (takeGreater.xor((pairs.first.size < pairs.second.size))) pairs.first
                else pairs.second
        index++
    }
    // at this point current list is 1
    return currentList.single().toInt(2)
}

// Splits a list into a onesList and a zeroesList
fun splitList(index: Int, input: List<String>): Pair<List<String>, List<String>> {
    var onesList: MutableList<String> = mutableListOf()
    var zeroesList: MutableList<String> = mutableListOf()

    input.forEach {
        if ("1".single().equals(it.get(index))) {
            onesList.add(it)
        } else {
            zeroesList.add(it)
        }
    }
    return Pair(onesList, zeroesList)
}
