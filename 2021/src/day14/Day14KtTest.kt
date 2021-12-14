package day14

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class Day14KtTest {
    val INPUT = listOf(
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

    @Test
    fun testExample() {
        val data = parseInput(INPUT)
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
    }

    @Test
    fun testBiggerExample() {
        val data = parseInput(INPUT)
        var countMap = runStepExtended(data.first, data.second, 10)
        assertEquals(298, countMap['C'])
        assertEquals(1749, countMap['B'])
        assertEquals(161, countMap['H'])
        assertEquals(865, countMap['N'])

        countMap = runStepExtended(data.first, data.second, 40)
        val diff = with(countMap.values.sortedByDescending { it }) {
            first() - last()
        }
        assertEquals(2188189693529, diff)
    }

    @Test
    fun testEntries() {
        val entries = getEntries('C', "NN")
        assertEquals("NC", entries[0])
        assertEquals("CN", entries[1])
    }


}