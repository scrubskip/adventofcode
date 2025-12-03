package day01

import java.io.File

fun main() {
    val input = File("src/day01/day01input.txt").readLines()

    println(getPassword(input))
    println(getPassword(input, true))
}

fun getPassword(instructions : List<String>, allClicks : Boolean = false) : Int {
    var position = 50
    var password = 0
    for (instruction in instructions) {
        var movement = instruction.substring(1).toInt()
        if (instruction.startsWith("L")) {
            movement = 0 - movement
        }
        if (allClicks) {
            password += if (movement < 0) {
                (position - 1).floorDiv( 100) - (position - 1 + movement).floorDiv(100)
            } else {
                (position + movement).floorDiv( 100) - (position.floorDiv(100))
            }
        }

        position = (position + movement) % 100
        if (!allClicks && position == 0) {
            password++
        }
    }
    return password
}