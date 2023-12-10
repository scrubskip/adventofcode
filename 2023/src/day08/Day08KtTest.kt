package day08

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class Day08KtTest {
    @Test
    fun testParse() {
        val input = listOf(
            "AAA = (BBB, CCC)",
            "BBB = (DDD, EEE)",
            "CCC = (ZZZ, GGG)",
            "DDD = (DDD, DDD)",
            "EEE = (EEE, EEE)",
            "GGG = (GGG, GGG)",
            "ZZZ = (ZZZ, ZZZ)"
        )

        val map = NodeMap(input)
        assertNotNull(map.getNode("AAA"))
        var node = map.getNode("AAA")
        assertEquals("BBB", node.left)
        assertEquals("CCC", node.right)

        node = map.getNode("EEE")
        assertEquals("EEE", node.left)
        assertEquals("EEE", node.right)
    }

    @Test
    fun testExample() {
        val input = listOf(
            "AAA = (BBB, CCC)",
            "BBB = (DDD, EEE)",
            "CCC = (ZZZ, GGG)",
            "DDD = (DDD, DDD)",
            "EEE = (EEE, EEE)",
            "GGG = (GGG, GGG)",
            "ZZZ = (ZZZ, ZZZ)"
        )
        val instructions = "RL"
        val map = NodeMap(input)
        assertEquals(2, runInstructions(instructions, map))
    }

    @Test
    fun testOtherExample() {
        val input = listOf(
            "AAA = (BBB, BBB)",
            "BBB = (AAA, ZZZ)",
            "ZZZ = (ZZZ, ZZZ)"
        )
        val instructions = "LLR"
        val map = NodeMap(input)
        assertEquals(6, runInstructions(instructions, map))
    }

    @Test
    fun testExampleGhost() {
        val input = listOf(
            "11A = (11B, XXX)",
            "11B = (XXX, 11Z)",
            "11Z = (11B, XXX)",
            "22A = (22B, XXX)",
            "22B = (22C, 22C)",
            "22C = (22Z, 22Z)",
            "22Z = (22B, 22B)",
            "XXX = (XXX, XXX)"
        )
        val instructions = "LR"
        val map = NodeMap(input)
        assertEquals(6, runInstructionsAsGhost(instructions, map))
    }
}