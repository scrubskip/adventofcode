package day04

import java.io.File

fun main() {

    val input = File("src/day04/day04input.txt").readLines()

    println(input.filter { isTotalOverlapping(parseSchedule(it)) }.size)
    println(input.filter { isAnyOverlapping(parseSchedule(it)) }.size)
}

val RANGE_REGEX: Regex = "(\\d+)-(\\d+),(\\d+)-(\\d+)".toRegex()

fun parseSchedule(input: String): Pair<IntRange, IntRange> {
    val match = RANGE_REGEX.matchEntire(input)?.groupValues?.drop(1)?.map { it.toInt() }
    return if (match != null) {
        Pair(match[0]..match[1], match[2]..match[3])
    } else {
        Pair(-1..-1, -1..-1)
    }
}

fun isTotalOverlapping(schedule: Pair<IntRange, IntRange>): Boolean {
    return (schedule.first.first <= schedule.second.first && schedule.first.last >= schedule.second.last) ||
            (schedule.second.first <= schedule.first.first && schedule.second.last >= schedule.first.last)
}

fun isAnyOverlapping(schedule: Pair<IntRange, IntRange>): Boolean {
    return schedule.first.contains(schedule.second.first) ||
            schedule.first.contains(schedule.second.last) ||
            schedule.second.contains(schedule.first.first) ||
            schedule.second.contains(schedule.first.last)
}