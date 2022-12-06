package day05

import kotlin.test.Test
import kotlin.test.assertEquals

internal class CratesTest {
    @Test
    fun testConstructor() {
        val input = listOf(
            "ZN",
            "MCD",
            "P"
        )
        val crates = Crates(input)
        assertEquals("NDP", crates.getMessage())

        val moves = listOf(
            "move 1 from 2 to 1",
            "move 3 from 1 to 3",
            "move 2 from 2 to 1",
            "move 1 from 1 to 2"
        )
        moves.forEach {
            crates.moveFromArg(it)
        }
        assertEquals("CMZ", crates.getMessage())
    }
}