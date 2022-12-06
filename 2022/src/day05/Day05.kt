package day05

import java.io.File

fun main() {
    val crates = Crates(File("src/day05/day05input_crates.txt").readLines())
    val moves = File("src/day05/day05input_moves.txt").readLines()

    moves.forEach {
        crates.moveFromArg(it)
    }
    println(crates.getMessage())
}

class Crates {

    private val _stacks: List<MutableList<Char>>

    val MOVE_PATTERN = "move (\\d+) from (\\d+) to (\\d+)".toRegex()

    constructor(input: List<String>) {

        this._stacks = input.map { it.toMutableList() }
    }

    fun moveFromArg(input: String) {
        // Parses the input
        val match = MOVE_PATTERN.matchEntire(input);
        if (match != null) {
            move(
                match.groupValues[1].toInt(), match.groupValues[2].toInt() - 1,
                match.groupValues[3].toInt() - 1
            )
        }
    }

    fun move(numItems: Int, startCol: Int, endCol: Int) {
        if (startCol < 0 || startCol >= this._stacks.size) {
            throw Exception("Invalid col: $startCol")
        }
        if (endCol < 0 || endCol >= this._stacks.size) {
            throw Exception("Invalid col: $endCol")
        }
        for (i in 0 until numItems) {
            this._stacks[endCol].add(this._stacks[startCol].removeLast())
        }
    }

    fun getMessage(): String {
        return this._stacks.map { it.last() }.joinToString(separator = "")
    }
}