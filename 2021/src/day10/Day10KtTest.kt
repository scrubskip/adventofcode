package day10

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class Day10KtTest {
    @Test
    fun testExample() {
        val input = listOf(
            "[({(<(())[]>[[{[]{<()<>>",
            "[(()[<>])]({[<{<<[]>>(",
            "{([(<{}[<>[]}>{[]{[(<()>",
            "(((({<>}<{<{<>}{[]{[]{}",
            "[[<[([]))<([[{}[[()]]]",
            "[{[{({}]{}}([{[{{{}}([]",
            "{<[[]]>}<{[{[{[]{()[[[]",
            "[<(<(<(<{}))><([]([]()",
            "<{([([[(<>()){}]>(<<{{",
            "<{([{{}}[<[[[<>{}]]]>[]]"
        )

        val corruptedLineChars = input.mapNotNull { getIllegalCharacter(it) }
        assertEquals(5, corruptedLineChars.size)
        assertEquals(26397, corruptedLineChars.sumOf { getScore(it) })
    }
}