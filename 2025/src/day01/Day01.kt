package day01

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val input = File("src/day01/day01input.txt").readLines()

    println(getPassword(input))
}

fun getPassword(instructions : List<String>) : Int {
    var position = 50
    var password = 0
    for (instruction in instructions) {
        var movement = instruction.substring(1).toInt()
        if (instruction.startsWith("L")) {
            movement = 0 - movement
        }
        position = (position + movement) % 100
        if (position == 0) {
            password++
        }
    }
    return password
}
