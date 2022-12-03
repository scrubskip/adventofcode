package day03

import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class Day03Test {
    @Test
    fun testGetItemPriority() {
        assertEquals('p', getDuplicateItemType("vJrwpWtwJgWrhcsFMMfFFhFp"))
        assertEquals(16, getPriority('p'))

        assertEquals('L', getDuplicateItemType("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"))
        assertEquals(38, getPriority('L'))
    }

    @Test
    fun testGetBadgeItem() {
        val input = listOf(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg"
        )
        assertEquals('r', getSharedItem(input))

        val input2 = listOf(
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw"
        )
        assertEquals('Z', getSharedItem(input2))
    }
}
