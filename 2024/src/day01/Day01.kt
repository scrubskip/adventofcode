package day01

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val input = File("src/day01/day01input.txt").readLines()
    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()

    input.forEach {
        val string = it.split(" ", limit = 2)
        list1.add(string[0].trim().toInt())
        list2.add(string[1].trim().toInt())
    }

    println(getDistance(list1, list2))
}

fun getDistance(list1: List<Int>, list2: List<Int>): Int {
    if (list1.size != list2.size) {
        throw Exception("Not the right size ${list1.size}, ${list2.size}")
    }

    val sorted1 = list1.sorted()
    val sorted2 = list2.sorted()

    return sorted1.withIndex().sumOf {
        (sorted2[it.index] - it.value).absoluteValue
    }
}
