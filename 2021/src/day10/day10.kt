package day10

import java.io.File

fun main() {
    val input = File("src/day10", "day10input.txt").readLines()
    println(input.mapNotNull { getIllegalCharacter(it) }.sumOf { getScore(it) })
}

fun getIllegalCharacter(input: String): Char? {
    val charMap: Map<Char, Char> = buildMap {
        put('(', ')')
        put('[', ']')
        put('{', '}')
        put('<', '>')
    }

    var openChars = ArrayDeque<Char>()
    for (char in input) {
        if (isClosedChar(char)) {
            // check the last open char
            if (openChars.size == 0) {
                // mismatched number
                return char
            }
            val lastOpenChar = openChars.removeLast()
            if (charMap[lastOpenChar] != char) {
                return char
            }
        }

        if (isOpenChar(char)) {
            openChars.add(char)
        }
    }
    return null
}

fun isOpenChar(char: Char): Boolean {
    return listOf('(', '[', '{', '<').contains(char)
}

fun isClosedChar(char: Char): Boolean {
    return listOf(')', ']', '}', '>').contains(char)
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