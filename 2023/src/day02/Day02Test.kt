package day02

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day02Test {
    @Test
    fun testParse() {
        val input = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"

        val game = Game(input)

        assertEquals(4, game.getMaxSeen("red"))
        assertEquals(6, game.getMaxSeen("blue"))
        assertEquals(2, game.getMaxSeen("green"))
    }

    @Test
    fun testValidGame() {
        val colorMap = mapOf<String, Int>(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )
        val input = "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"

        val game = Game(input)
        assertTrue(game.isValidGame(colorMap))
    }

    @Test
    fun testInvalidGame() {
        val colorMap = mapOf<String, Int>(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )
        val input = "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"

        val game = Game(input)
        assertFalse(game.isValidGame(colorMap))
    }
}