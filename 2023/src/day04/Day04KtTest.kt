package day04

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day04KtTest {
    @Test
    fun testParse() {
        val input = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
        val scratcher = Scratcher(input)

        assertEquals(8, scratcher.getPointValue())
        assertEquals(1, scratcher.id)
    }

    @Test
    fun testNoPoints() {
        val input = "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36"
        val scratcher = Scratcher(input)
        assertEquals(0, scratcher.getPointValue())
    }

    @Test
    fun testOnePoint() {
        val input = "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83"
        val scratcher = Scratcher(input)
        assertEquals(1, scratcher.getPointValue())
    }

    @Test
    fun testPileCardCount() {
        val input = listOf(
            "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
            "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
            "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
            "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
            "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
            "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
        )

        val pile = Pile(input)
        assertEquals(30, pile.getCardCount())
    }
}