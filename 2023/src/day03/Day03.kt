package day03

import java.io.File

fun main() {
    val input = File("src/day03/Day03.txt").readLines()
    val schematic = Schematic(input)

    println(schematic.getPartNumbers().sum())
}

typealias Coordinate = Pair<Int, Int>

val NUMBER_REGEX = Regex("(\\d+)")
val SYMBOL_REGEX = Regex("[^\\d\\.]")

class Schematic(entries: List<String>) {
    private val symbolMap: MutableMap<Coordinate, String> = mutableMapOf()
    private val numberMap: MutableMap<Coordinate, Int> = mutableMapOf()

    init {
        entries.forEachIndexed { rowIndex: Int, row: String ->
            NUMBER_REGEX.findAll(row).forEach {
                //println("adding number ${it.value.toInt()}")
                numberMap[Coordinate(rowIndex, it.range.first)] = it.value.toInt()
            }
            SYMBOL_REGEX.findAll(row).forEach {
                // println("adding symbol ${it.value}")
                symbolMap[Coordinate(rowIndex, it.range.first)] = it.value
            }
        }
    }

    fun getPartNumbers(): List<Int> {
        return numberMap.entries.filter {
            isTouchingSymbol(it.value.toString(), it.key)
        }.map { it.value }
    }

    fun isTouchingSymbol(numberString: String, coordinate: Coordinate): Boolean {
        for (row in coordinate.first - 1..coordinate.first + 1) {
            for (col in (coordinate.second - 1..coordinate.second + numberString.length)) {
                if (symbolMap.containsKey(Coordinate(row, col))) {
                    return true
                }
            }
        }
        return false
    }
}

