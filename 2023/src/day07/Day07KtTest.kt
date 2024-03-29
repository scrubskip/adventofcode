package day07

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test
import kotlin.test.assertTrue

class Day07KtTest {
    @Test
    fun testHandTypes() {
        assertEquals(HandType.FIVE_OF_A_KIND, Hand("AAAAA").handType)
        assertEquals(HandType.FOUR_OF_A_KIND, Hand("AA8AA").handType)
        assertEquals(HandType.FULL_HOUSE, Hand("23332").handType)
        assertEquals(HandType.THREE_OF_A_KIND, Hand("TTT98").handType)
        assertEquals(HandType.TWO_PAIR, Hand("23432").handType)
        assertEquals(HandType.ONE_PAIR, Hand("A23A4").handType)
        assertEquals(HandType.HIGH_CARD, Hand("23456").handType)
    }

    @Test
    fun testCompare() {
        assertTrue(Hand("AAAAA") > Hand("AA8AA"))
        assertTrue(Hand("AA8AA") > Hand("23332"))
        assertTrue(Hand("23332") > Hand("TTT98"))
        assertTrue(Hand("TTT98") > Hand("23432"))

        assertTrue(Hand("33332") > Hand("2AAAA"))
        assertTrue(Hand("77888") > Hand("77788"))
    }

    @Test
    fun testExample() {
        val input = listOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483"
        )

        assertEquals(6440, getWinnings(input))
    }

    @Test
    fun testJoker() {
        assertEquals(HandType.FIVE_OF_A_KIND, Hand("AAAAA", true).handType)
        assertEquals(HandType.FOUR_OF_A_KIND, Hand("QJJQ2", true).handType)

        assertEquals(HandType.THREE_OF_A_KIND, Hand("JJ5Q2", true).handType)
        assertEquals(HandType.FULL_HOUSE, Hand("JQQ22", true).handType)

        assertEquals(HandType.THREE_OF_A_KIND, Hand("JQT22", true).handType)

        assertEquals(HandType.ONE_PAIR, Hand("JQT32", true).handType)
        assertEquals(HandType.THREE_OF_A_KIND, Hand("7J478", true).handType)
        assertEquals(HandType.THREE_OF_A_KIND, Hand("6QJ22", true).handType)
        assertEquals(HandType.THREE_OF_A_KIND, Hand("T42JT", true).handType)
        assertEquals(HandType.FOUR_OF_A_KIND, Hand("999TJ", true).handType)
        assertEquals(HandType.FIVE_OF_A_KIND, Hand("JJJJJ", true).handType)

        assertTrue(Hand("JKKK2", true) < Hand("QQQQ2", true))
    }

    @Test
    fun testExampleWithJoker() {
        val input = listOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483"
        )

        assertEquals(5905, getWinnings(input, true))
    }
}