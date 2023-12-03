package day03

import java.io.File

fun main() {
    val input = File("src/day03/Day03.txt").readLines()
    val schematic = Schematic(input)

    println(schematic.getPartNumbers().sum())
    println(schematic.getGearRatios().sum())
}

typealias Coordinate = Pair<Int, Int>

data class Rectangle(val top: Int, val left: Int, val bottom: Int, val right: Int, val value: Int) {
    fun touches(coordinate: Coordinate): Boolean {
        return coordinate.first in top - 1..bottom + 1
                && coordinate.second in left - 1..right + 1
    }
}

val NUMBER_REGEX = Regex("(\\d+)")
val SYMBOL_REGEX = Regex("[^\\d\\.]")

class Schematic(entries: List<String>) {
    private val symbolMap: MutableMap<Coordinate, String> = mutableMapOf()
    private val numberMap: MutableMap<Coordinate, Rectangle> = mutableMapOf()
    private var maxNumberLength = 0

    init {
        entries.forEachIndexed { rowIndex: Int, row: String ->
            NUMBER_REGEX.findAll(row).forEach {
                //println("adding number ${it.value.toInt()}")
                numberMap[Coordinate(rowIndex, it.range.first)] = Rectangle(
                    rowIndex, it.range.first, rowIndex, it.range.last,
                    it.value.toInt()
                )
                if (it.value.length > maxNumberLength) {
                    maxNumberLength = it.value.length
                }
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
        }.map { it.value.value }
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

    fun getGearRatios(): List<Int> {
        // First go through the symbol map and find "*"
        val gearRatios = mutableListOf<Int>()
        symbolMap.entries.filter { it.value == "*" }.forEach {
            // Each gear has 2 numbers touching it
            val foundNumber = mutableListOf<Int>()
            for (row in it.key.first - 1..it.key.first + 1) {
                for (col in it.key.second - maxNumberLength - 1..it.key.second + 1) {
                    if (numberMap.containsKey(Coordinate(row, col))) {
                        // Check that the number is touching
                        val rect = numberMap[Coordinate(row, col)]
                        if (rect!!.touches(it.key)) {
                            foundNumber.add(rect.value)
                        }
                    }
                }
            }
            if (foundNumber.size == 2) {
                gearRatios.add(foundNumber[0] * foundNumber[1])
            }
        }
        return gearRatios
    }
}

