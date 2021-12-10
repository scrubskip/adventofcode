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

        assertEquals("}}]])})]", getCompletionString("[({(<(())[]>[[{[]{<()<>>"))
        assertEquals(")}>]})", getCompletionString("[(()[<>])]({[<{<<[]>>("))

        assertEquals(288957, scoreCompletionString("}}]])})]"))
        assertEquals(5566, scoreCompletionString(")}>]})"))
        assertEquals(1480781, scoreCompletionString("}}>}>))))"))
        assertEquals(995444, scoreCompletionString("]]}}]}]}>"))
        assertEquals(294, scoreCompletionString("])}>"))

        val scores = input.filter { getIllegalCharacter(it) == null }
            .map { scoreCompletionString(getCompletionString(it)) }
            .sorted()
        assertEquals(288957, scores.sorted()[(scores.size - 1) / 2])
    }
}