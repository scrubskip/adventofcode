package day03

import java.io.File

fun main() {
    val input = File("src/day03/day03input.txt").readLines()
    println(input.sumOf { getMult(it) })

    println(getMultWithDo(input.joinToString(separator = "")))
}

val MULT_REGEX = Regex("mul\\((\\d+),(\\d+)\\)")

val DO_STRING = "do()"
val DONT_STRING = "don\'t()"

fun getMult(input : String) : Int {
    val matchResults = MULT_REGEX.findAll(input)
    return matchResults.map { it.groupValues[1].toInt() * it.groupValues[2].toInt() }.sum()
}


fun getMultWithDo(input : String) : Int {
    var index = 0
    var result = 0
    while (index < input.length && index != -1) {
        if (index > 0) {
            index += DO_STRING.length
        }
        var dontIndex = input.indexOf(DONT_STRING, index)
        if (dontIndex == -1) {
            dontIndex = input.length - 1
        }
        var enabledString = input.substring(index, dontIndex)
        // println(enabledString)
        result += getMult(enabledString)
        // now check the next do after don't index
        if (dontIndex < input.length) {
            index = input.indexOf(DO_STRING, dontIndex)
        } else {
            break
        }
    }
    return result
}