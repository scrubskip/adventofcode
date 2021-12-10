package day10

import java.io.File

fun main() {
    val input = File("src/day10", "day10input.txt").readLines()
    println(input.mapNotNull { getIllegalCharacter(it) }.sumOf { getScore(it) })

    val scores = input.filter { getIllegalCharacter(it) == null }
        .map { scoreCompletionString(getCompletionString(it)) }
    println(scores)
    println(scores.sorted()[(scores.size - 1) / 2])

}

private val CHAR_MAP: Map<Char, Char> = buildMap {
    put('(', ')')
    put('[', ']')
    put('{', '}')
    put('<', '>')
}

private val CLOSE_CHARS = listOf(')', ']', '}', '>')

fun getIllegalCharacter(input: String): Char? {
    var openChars = ArrayDeque<Char>()
    for (char in input) {
        if (isClosedChar(char)) {
            // check the last open char
            if (openChars.size == 0) {
                // mismatched number
                return char
            }
            val lastOpenChar = openChars.removeLast()
            if (CHAR_MAP[lastOpenChar] != char) {
                return char
            }
        }

        if (isOpenChar(char)) {
            openChars.add(char)
        }
    }
    return null
}

fun getCompletionString(input: String): String {
    var openChars = ArrayDeque<Char>()
    for (char in input) {
        if (isClosedChar(char)) {
            // check the last open char
            if (openChars.size == 0) {
                throw Exception("Corrupted string")
            }
            val lastOpenChar = openChars.removeLast()
            if (CHAR_MAP[lastOpenChar] != char) {
                throw Exception("Corrupted string")
            }
        }

        if (isOpenChar(char)) {
            openChars.add(char)
        }
    }
    // now, go through the openChars, get the right closing chars in order
    return openChars.reversed().map { CHAR_MAP[it] }.joinToString(separator = "", postfix = "")
}

fun scoreCompletionString(input: String): Long {
    return input.toCharArray().fold(0) { acc, char ->
        CLOSE_CHARS.indexOf(char) + 1 + (acc * 5)
    }
}

fun isOpenChar(char: Char): Boolean {
    return listOf('(', '[', '{', '<').contains(char)
}

fun isClosedChar(char: Char): Boolean {
    return CLOSE_CHARS.contains(char)
}

fun getScore(char: Char): Int {
    return when (char) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> throw Exception("invalid char $char")
    }
}