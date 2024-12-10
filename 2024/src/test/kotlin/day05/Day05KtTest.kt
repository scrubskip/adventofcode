package day05

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Day05KtTest {

    val RULES_INPUT = """
            47|53
            97|13
            97|61
            97|47
            75|29
            61|13
            75|53
            29|13
            97|29
            53|29
            61|53
            97|53
            61|29
            47|13
            75|47
            97|75
            47|61
            75|61
            47|29
            75|13
            53|13
        """.trimIndent().lines()

    val EXAMPLE_INPUT = """
            75,47,61,53,29
            97,61,53,29,13
            75,29,13
            75,97,47,61,53
            61,13,29
            97,13,75,29,47
        """.trimIndent().lines().map { update -> update.split(",").map { it.toInt() } }

    @Test
    fun getRules() {
        val rules = getRules(RULES_INPUT)
        assertEquals(setOf(53, 13, 61, 29), rules[47])
        assertEquals(setOf(13, 61, 47, 29, 53, 75), rules[97])
    }

    @Test
    fun isPageOrdered() {
        val rules = getRules(RULES_INPUT)
        assertTrue(isPageOrdered(EXAMPLE_INPUT[0], rules))
        assertTrue(isPageOrdered(EXAMPLE_INPUT[1], rules))
        assertTrue(isPageOrdered(EXAMPLE_INPUT[2], rules))
        assertFalse(isPageOrdered(EXAMPLE_INPUT[3], rules))
        assertFalse(isPageOrdered(EXAMPLE_INPUT[4], rules))
        assertFalse(isPageOrdered(EXAMPLE_INPUT[5], rules))
    }

    @Test
    fun getMiddles() {
        val rules = getRules(RULES_INPUT)
        println(getMiddles(EXAMPLE_INPUT, rules))
        assertEquals(143, getMiddles(EXAMPLE_INPUT, rules).sum())
    }
}