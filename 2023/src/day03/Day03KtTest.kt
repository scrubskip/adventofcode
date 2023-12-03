package day03

import junit.framework.TestCase.assertFalse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day03KtTest {
    @Test
    fun testParse() {
        val input = listOf(
            "467..114..",
            "...*......",
            "..35..633.",
            "......#...",
            "617*......",
            ".....+.58.",
            "..592.....",
            "......755.",
            "...$.*....",
            ".664.598.."
        )
        val schematic = Schematic(input)
    }

    @Test
    fun testIsTouching() {
        val input = listOf(
            "467..114..",
            "...*......",
            "..35..633.",
            "......#...",
            "617*......",
            ".....+.58.",
            "..592.....",
            "......755.",
            "...$.*....",
            ".664.598.."
        )
        val schematic = Schematic(input)

        assertTrue(schematic.isTouchingSymbol("467", Coordinate(0, 0)))
        assertFalse(schematic.isTouchingSymbol("114", Coordinate(0, 5)))
        assertTrue(schematic.isTouchingSymbol("617", Coordinate(4, 0)))
        assertFalse(schematic.isTouchingSymbol("58", Coordinate(5, 7)))

        // println("${schematic.getPartNumbers()}")
        assertEquals(4361, schematic.getPartNumbers().sum())
    }

    @Test
    fun testGearRatios() {
        val input = listOf(
            "467..114..",
            "...*......",
            "..35..633.",
            "......#...",
            "617*......",
            ".....+.58.",
            "..592.....",
            "......755.",
            "...$.*....",
            ".664.598.."
        )
        val schematic = Schematic(input)
        val gearRatios = schematic.getGearRatios()
        assertEquals(2, gearRatios.size)
        assertEquals(16345, gearRatios[0])
        assertEquals(451490, gearRatios[1])
        assertEquals(467835, gearRatios.sum())
    }
}