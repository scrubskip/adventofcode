package day08

import java.io.File
import kotlin.math.pow
import kotlin.streams.toList

fun main() {
    val input = File("src/day08", "day8input.txt").readLines().map { parseLine(it) }
    println(getUniqueSegments(input.map { it.second }).size)

    val outputSum = input.sumOf {
        val digitMap = analyzeInput(it.first)
        parseOutput(it.second, digitMap)
    }
    println(outputSum)
}


/**
 * Input a line of input signal and output separated by |
 * Output a pair with the input signals in one list and the output signals in 1 list
 */
fun parseLine(input: String): Pair<List<String>, List<String>> {
    val inputList = input.split("|").map { it.trim() }
    return Pair(inputList[0].split(" "), inputList[1].split(" "))
}

fun getUniqueSegments(segments: List<List<String>>): List<String> {
    return segments.flatten().filter { isUniqueSegments(it) }
}

fun isUniqueSegments(segment: String): Boolean {
    return when (segment.length) {
        2 -> true
        3 -> true
        4 -> true
        7 -> true
        else -> false
    }
}

/**
 * Analyzes 10 input strings and returns a mapping from String to int segment
 */
fun analyzeInput(input: List<String>): Map<String, Int> {
    val sortedInput = input.sortedBy { it.length }
    // Maps number 0 -> 9 to the input string.
    val numberMap: MutableMap<Int, String> = mutableMapOf()
    // Stores the map of position to segment
    /*
         0
       1   2
         3
       4   5
         6
     */
    val segmentMap: MutableMap<Int, Char> = mutableMapOf()
    // The first item should be length 2 and correspond to 1
    if (sortedInput[0].length == 2) {
        numberMap[1] = sortedInput[0]
    } else throw Exception("Could not find 1")
    // The second item should be length 3 and correspond to 7
    if (sortedInput[1].length == 3) {
        numberMap[7] = sortedInput[1]
    } else throw Exception("Could not find 7")
    if (sortedInput[2].length == 4) {
        numberMap[4] = sortedInput[2]
    } else throw Exception("Could not find 4")
    if (sortedInput[9].length == 7) {
        numberMap[8] = sortedInput[9]
    } else throw Exception("Could not find 8")
    // Now from these strings, figure out the 2, 7 and 0 segments
    // Top segment is whichever part of 7 is not in 1
    segmentMap[0] = findUniqueChar(numberMap[7], numberMap[1])
    val segOneFourCandidates = findUniqueChars(numberMap[4], numberMap[1])
    if (segOneFourCandidates.size != 2) throw Exception("Too many chars between 1 and 4 $segOneFourCandidates")

    val zeroSixNineCandidates = input.filter { it.length == 6 }
    if (zeroSixNineCandidates.size != 3) throw Exception("Didn't find right number of 6 segments")

    numberMap[6] = zeroSixNineCandidates.filterNot { containsAll(it, numberMap[1]) }.first()
    numberMap[9] = zeroSixNineCandidates.first { containsAll(it, numberMap[4]) }
    numberMap[0] = zeroSixNineCandidates.first { !containsAll(it, numberMap[4]) && it != numberMap[6] }

    // Now we can get 5 and 3 which are the only numbers that are 1 away from 9 that are also length 5
    val fiveThreeCandidates = input.filter { it.length == 5 && findUniqueChars(numberMap[9], it).size == 1 }
    if (fiveThreeCandidates.size != 2) throw Exception("Too many strings match 5 and 3")

    // Now one of these contains both elements of 1 and one of these doesn't.
    if (containsAll(fiveThreeCandidates[0], numberMap[1])) {
        numberMap[3] = fiveThreeCandidates[0]
        numberMap[5] = fiveThreeCandidates[1]
    } else {
        numberMap[3] = fiveThreeCandidates[1]
        numberMap[5] = fiveThreeCandidates[0]
    }

    segmentMap[1] = findUniqueChar(numberMap[9], numberMap[3])
    segmentMap[2] = findUniqueChar(numberMap[9], numberMap[6])
    // Now we have segment 4 which is the only letter not in 9
    segmentMap[4] = findUniqueChar(numberMap[8], numberMap[9])
    segmentMap[3] = findUniqueChar(numberMap[8], numberMap[0])
    segmentMap[5] = if (numberMap[1]!![0] == segmentMap[2]) numberMap[1]!![1] else numberMap[1]!![0]
    segmentMap[6] = findUniqueChar(numberMap[8], numberMap[4] + segmentMap[0] + segmentMap[4])

    // Now return a map of char to segment
    return makeDigitMapFromSegmentMap(segmentMap)
}

fun findUniqueChars(base: String?, compare: String?): List<Char> {
    return base!!.chars().toList().toSet().subtract(compare!!.chars().toList().toSet()).map { it.toChar() }
}

fun findUniqueChar(base: String?, compare: String?): Char {
    // Finds the 1 unique char or throws and exception
    val diffSet = findUniqueChars(base, compare)
    if (diffSet.size == 1) {
        return diffSet.first()
    }
    throw Exception("Found too many characters between $base and $compare")
}

fun containsAll(base: String?, all: String?): Boolean {
    return all!!.all { base!!.contains(it) }
}

/**
 * Takes a segment map and makes a sorted string to digit map.
 */
fun makeDigitMapFromSegmentMap(segmentMap: Map<Int, Char>): Map<String, Int> {
    return buildMap {
        put(makeSortedString(listOf(0, 1, 2, 4, 5, 6), segmentMap), 0)
        put(makeSortedString(listOf(2, 5), segmentMap), 1)
        put(makeSortedString(listOf(0, 2, 3, 4, 6), segmentMap), 2)
        put(makeSortedString(listOf(0, 2, 3, 5, 6), segmentMap), 3)
        put(makeSortedString(listOf(1, 2, 3, 5), segmentMap), 4)
        put(makeSortedString(listOf(0, 1, 3, 5, 6), segmentMap), 5)
        put(makeSortedString(listOf(0, 1, 3, 4, 5, 6), segmentMap), 6)
        put(makeSortedString(listOf(0, 2, 5), segmentMap), 7)
        put(makeSortedString(listOf(0, 1, 2, 3, 4, 5, 6), segmentMap), 8)
        put(makeSortedString(listOf(0, 1, 2, 3, 5, 6), segmentMap), 9)
    }
}

fun makeSortedString(input: List<Int>, segmentMap: Map<Int, Char>): String {
    return input.map { segmentMap.getOrDefault(it, 'z').toChar() }.sorted().joinToString()
}

fun sortString(input: String): String {
    return input.toCharArray().sorted().joinToString()
}

fun parseOutput(output: List<String>, digitMap: Map<String, Int>): Int {
    return output.reversed().foldIndexed(0) { index, acc, string ->
        (digitMap.getOrDefault(sortString(string), 0) * (10.0.pow(index)) + acc).toInt()
    }
}
