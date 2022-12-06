package day06

import java.io.File

fun main() {
    val input = File("src/day06/day06input.txt").readLines().first()
    println(getFirstMarkerIndex(input))
}

fun getFirstMarkerIndex(input: String): Int {
    if (input.length < 4) {
        throw Exception("Input is too short")
    }

    for (index in 0..input.length - 4) {
        if (input.substring(index, index + 4).toSet().size == 4) {
            return index + 4
        }
    }
    return -1
}