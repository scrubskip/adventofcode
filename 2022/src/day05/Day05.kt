package day05

import java.io.File

fun main() {
    val crates = Crates(File("src/day05/day05input_crates.txt").readLines(), true)
    val moves = File("src/day05/day05input_moves.txt").readLines()

    moves.forEach {
        crates.moveFromArg(it)
    }
    println(crates.getMessage())
}

class Crates {

    private val _stacks: List<MutableList<Char>>
    private val _moveAll: Boolean

    val MOVE_PATTERN = "move (\\d+) from (\\d+) to (\\d+)".toRegex()

    constructor(input: List<String>, moveAll: Boolean = false) {

        this._stacks = input.map { it.toMutableList() }
        this._moveAll = moveAll
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

    private fun move(numItems: Int, startCol: Int, endCol: Int) {
        if (startCol < 0 || startCol >= this._stacks.size) {
            throw Exception("Invalid col: $startCol")
        }
        if (endCol < 0 || endCol >= this._stacks.size) {
            throw Exception("Invalid col: $endCol")
        }
        if (this._moveAll) {
            this._stacks[endCol].addAll(this._stacks[startCol].takeLast(numItems))
            for (i in 0 until numItems) {
                this._stacks[startCol].removeLast()
            }
        } else {
            for (i in 0 until numItems) {
                this._stacks[endCol].add(this._stacks[startCol].removeLast())
            }
        }
    }

    fun getMessage(): String {
        return this._stacks.map { it.last() }.joinToString(separator = "")
    }
}