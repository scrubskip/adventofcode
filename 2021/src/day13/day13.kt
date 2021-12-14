package day13

import java.io.File

fun main() {
    val data = parseInput(File("src/day13", "day13input.txt").readLines())

    var board = data.first
    for (instruction in data.second) {
        println("folding $instruction ${board.size}")
        board = foldAlong(board, instruction.first, instruction.second)
    }
    println(board.size)

    printOutput(board)
}

fun parseInput(input: List<String>): Pair<Set<Pair<Int, Int>>, List<Pair<Char, Int>>> {
    val pointSet = mutableSetOf<Pair<Int, Int>>()
    val instructionList = mutableListOf<Pair<Char, Int>>()

    val splitList = input.filter { it.isNotBlank() }
        .partition { !it.startsWith("fold along") }

    pointSet.addAll(splitList.first.map {
        with(it.split(",").map { coordinate -> coordinate.toInt() }) {
            Pair(get(0), get(1))
        }
    })

    instructionList.addAll(splitList.second.map { instruction ->
        instruction.indexOf("=").let {
            Pair(instruction[it - 1], instruction.substring(it + 1).toInt())
        }
    })

    return Pair(pointSet, instructionList)
}

fun foldAlong(data: Set<Pair<Int, Int>>, axis: Char, coordinate: Int): Set<Pair<Int, Int>> {
    // Iterate through the points. If a point is > the axis / coordinate,
    // add its mirror, otherwise just add the point
    val returnSet = mutableSetOf<Pair<Int, Int>>()
    for (point in data) {
        var x = point.first
        var y = point.second
        when (axis) {
            'x' -> if (point.first > coordinate) x = coordinate - (x - coordinate)
            'y' -> if (point.second > coordinate) y = coordinate - (y - coordinate)
        }
        returnSet.add(Pair(x, y))
    }
    return returnSet
}

fun printOutput(data: Set<Pair<Int, Int>>) {
    // find max x and max y
    var maxX: Int = 0
    var maxY: Int = 0
    data.forEach {
        if (it.first > maxX) maxX = it.first
        if (it.second > maxY) maxY = it.second
    }
    for (row in 0..maxY) {
        println((0..maxX).joinToString(separator = "", postfix = "") {
            when (data.contains(Pair(it, row))) {
                true -> "#"
                false -> "."
            }
        })
    }
}