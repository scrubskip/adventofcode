package day15

import java.io.File

fun main() {
    val input = File("src/day15/day15.txt").readLines().first().split(",")
    println(input.sumOf { getHash(it) })

}

fun getHash(input: String): Int {
    var currentValue = 0
    input.forEach {
        currentValue += it.code
        currentValue *= 17
        currentValue %= 256
    }
    return currentValue
}