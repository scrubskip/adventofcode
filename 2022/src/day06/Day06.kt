package day06

import java.io.File

fun main() {
    val input = File("src/day06/day06input.txt").readLines().first()
    println(getFirstMarkerIndex(input))
    println(getMessageIndex(input))
}

fun getFirstMarkerIndex(input: String): Int {
    return getDistinctChars(input, 4)
}

fun getMessageIndex(input: String): Int {
    return getDistinctChars(input, 14)
}

fun getDistinctChars(input: String, length: Int): Int {
    if (input.length < length) {
        throw Exception("Input is too short")
    }

    for (index in 0..input.length - length) {
        if (input.substring(index, index + length).toSet().size == length) {
            return index + length
        }
    }
    return -1
}