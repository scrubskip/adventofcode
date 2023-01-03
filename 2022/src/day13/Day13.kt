package day13

import java.io.File
import java.util.*

fun main() {
    runFileInput("src/day13/day13input.txt")

    val input = mutableListOf<String>()
    input.addAll(File("src/day13/day13input.txt").readLines())
    input.add("[[2]]")
    input.add("[[6]]")
    println(getSortedIndexMultiplier(input.filter { it.isNotBlank() }.map { parseInput(it) }))
}

fun runFileInput(inputFile: String): Int {
    val input = File(inputFile).readLines()
    val indices = mutableListOf<Int>()

    input.filter { it.isNotBlank() }.chunked(2).forEachIndexed { index, values ->
        if (compareLists(parseInput(values[0]), parseInput(values[1])) == -1) {
            indices.add(index + 1)
            println("Found ${values[0]} < ${values[1]}")
        }
    }
    val count = input.filter { it.isNotBlank() }.chunked(2).size
    println("compared $count pairs and found ${indices.size} correct")
    println(indices.sum())
    return indices.sum()
}

fun getSortedIndexMultiplier(input: List<List<Any>>): Int {
    val sortedList = input.sortedWith { left, right -> compareLists(left, right) }
    val indexLow = sortedList.indexOf(listOf(listOf(2))) + 1
    val indexHigh = sortedList.indexOf(listOf(listOf(6))) + 1
    return indexLow * indexHigh
}


fun parseInput(input: String): List<Any> {
    if (input.isEmpty() || !input.startsWith("[")) {
        throw Exception("invalid input: $input")
    }
    val stack = Stack<MutableList<Any>>()
    var currentList = stack.push(mutableListOf())
    var index = 1
    var valIndex = -1

    while (index < input.length) {
        when (val currentChar = input[index]) {
            '[' -> {
                val newList = mutableListOf<Any>()
                currentList.add(newList)
                stack.push(currentList)
                currentList = newList
            }
            ']', ',' -> {
                if (valIndex >= 0) {
                    // Parse from valIndex to here
                    currentList?.add(input.substring(valIndex, index).toInt())
                    valIndex = -1
                }
                if (currentChar == ']') {
                    currentList = stack.pop()
                }
            }
            else -> {
                if (valIndex == -1 && currentChar.isDigit()) {
                    valIndex = index
                }
            }
        }
        index++
    }

    return currentList
}

/**
 * @return -1 if left is lower, 1 if right is higher, 0 if equal
 */
@Suppress("UNCHECKED_CAST")
fun compareLists(left: List<Any>, right: List<Any>): Int {
    if (left.isEmpty() && right.isNotEmpty()) {
        // left ran out of items
        return -1
    }

    var index = 0
    while (index < left.size) {
        var leftVal = left[index]
        if (index > right.size - 1) {
            // right ran out of items
            return 1
        }
        var rightVal = right[index]
        if (leftVal is Int && rightVal is Int) {
            if (leftVal > rightVal) {
                return 1
            } else if (leftVal < rightVal) {
                return -1
            }
        } else {
            var listReturn = -2
            if (leftVal is List<*> && rightVal is List<*>) {
                listReturn = compareLists(leftVal as List<Any>, rightVal as List<Any>)
            } else if (leftVal is Int && rightVal is List<*>) {
                listReturn = compareLists(listOf(leftVal), rightVal as List<Any>)
            } else if (leftVal is List<*> && rightVal is Int) {
                listReturn = compareLists(leftVal as List<Any>, listOf(rightVal))
            }
            if (listReturn == -2) {
                throw Exception("Invalid items in list.")
            }
            if (listReturn != 0) {
                return listReturn
            }
        }
        index++
    }
    // Ran out of left items. If the right is also out of items
    return if (index > right.size) 1 else if (index < right.size) -1 else 0
}