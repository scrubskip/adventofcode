package day15

import java.io.File

fun main() {
    val input = File("src/day15/day15.txt").readLines().first().split(",")
    println(input.sumOf { getHash(it) })
    val boxes = getBoxes(input)
    // println(boxes.map { "${it.boxNumber} ${it.getFocusPower()}" })
    println(boxes.sumOf { it.getFocusPower() })

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

data class Lens(val label: String, var focalLength: Int)

class Box(val boxNumber: Int) {

    private val lensList = mutableListOf<Lens>()

    fun doOperation(label: String, operation: String) {
        if ("-" == operation) {
            lensList.removeIf { it.label == label }
        } else if ('=' == operation.first()) {
            val found = lensList.find { it.label == label }
            val focalLength = operation.drop(1).toInt()
            if (found != null) {
                found.focalLength = focalLength
            } else {
                lensList.add(Lens(label, focalLength))
            }
        }
    }

    fun getFocusPower(): Int {
        return lensList.mapIndexed { index, lens -> (boxNumber + 1) * (index + 1) * lens.focalLength }
            .sum()
    }
}

val LABEL = Regex("""(\w+)""")

fun getBoxes(input: List<String>): List<Box> {
    var boxMap = mutableMapOf<Int, Box>()
    input.forEach {
        val instruction = it
        val match = LABEL.find(instruction)
        match?.let {
            val label = match.value
            val boxIndex = getHash(label)
            val box = boxMap.getOrPut(boxIndex) { Box(boxIndex) }
            box.doOperation(label, instruction.substring(match.range.last + 1))
        }

    }
    return boxMap.keys.sorted().map { boxMap[it]!! }
}