package day16

import kotlin.test.Test
import kotlin.test.assertEquals

class Day16KtTest {
    @Test
    fun testParse() {
        val input = listOf(
            """.|...\....""",
            """|.-.\.....""",
            """.....|-...""",
            """........|.""",
            """..........""",
            """.........\""",
            """..../.\\..""",
            """.-.-/..|..""",
            """.|....-|.\""",
            """..//.|....""",
        )

        val caveMap = CaveMap(input)
        assertEquals(9, caveMap.getMaxWidth())
        assertEquals(9, caveMap.getMaxHeight())

        caveMap.startBeam()
        assertEquals(46, caveMap.energizedTiles.size)
    }

    @Test
    fun testFindMax() {
        val input = listOf(
            """.|...\....""",
            """|.-.\.....""",
            """.....|-...""",
            """........|.""",
            """..........""",
            """.........\""",
            """..../.\\..""",
            """.-.-/..|..""",
            """.|....-|.\""",
            """..//.|....""",
        )

        val caveMap = CaveMap(input)

        caveMap.startBeam(Beam(Coordinate(3, 0), 0, 1))
        assertEquals(51, caveMap.energizedTiles.size)

        assertEquals(51, caveMap.findMax())
    }
}