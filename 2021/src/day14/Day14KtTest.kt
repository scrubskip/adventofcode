package day14

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class Day14KtTest {
    @Test
    fun testExample() {
        val input = listOf(
            "NNCB",
            "",
            "CH -> B",
            "HH -> N",
            "CB -> H",
            "NH -> C",
            "HB -> C",
            "HC -> B",
            "HN -> C",
            "NN -> C",
            "BH -> H",
            "NC -> B",
            "NB -> B",
            "BN -> B",
            "BB -> N",
            "BC -> B",
            "CC -> N",
            "CN -> C"
        )

        val data = parseInput(input)
        assertEquals("NNCB", data.first)
        assertEquals(16, data.second.size)
        assertEquals('B', data.second["CH"])

        var chain = data.first
        chain = runStep(chain, data.second)
        assertEquals("NCNBCHB", chain)
        chain = runStep(chain, data.second)
        assertEquals("NBCCNBBBCBHCB", chain)
        chain = runStep(chain, data.second)
        assertEquals("NBBBCNCCNBBNBNBBCHBHHBCHB", chain)
        chain = runStep(chain, data.second)
        assertEquals("NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB", chain)
        for (i in 5..10) {
            chain = runStep(chain, data.second)
        }
        assertEquals(1588, getQuantityDifference(chain))

        for (i in 11..40) {
            chain = runStep(chain, data.second)
        }
        assertEquals(2188189693529, getQuantityDifference(chain))
    }
}