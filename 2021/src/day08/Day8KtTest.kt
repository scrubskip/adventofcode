package day08

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class Day8KtTest {
    @Test
    fun testParse() {
        val input = "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | " +
                "fdgacbe cefdb cefbgd gcbe"
        val parsed = parseLine(input)
        assertEquals(
            listOf("be", "cfbegad", "cbdgef", "fgaecd", "cgeb", "fdcge", "agebfd", "fecdb", "fabcd", "edb"),
            parsed.first
        )
        assertEquals(listOf("fdgacbe", "cefdb", "cefbgd", "gcbe"), parsed.second)
    }

    @Test
    fun testExample() {
        val input = listOf(
            "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe",
            "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc",
            "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg",
            "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb",
            "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea",
            "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb",
            "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe",
            "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef",
            "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb",
            "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce"
        )
        assertEquals(26, getUniqueSegments(input.map { parseLine(it) }.map { it.second }).size)

        var digitMap = analyzeInput("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab".split(" "))
        assertEquals(5353, parseOutput("cdfeb fcadb cdfeb cdbaf".split(" "), digitMap))

        val outputSum = input.map { parseLine(it) }.sumOf {
            val digitMap = analyzeInput(it.first)
            println(parseOutput(it.second, digitMap))
            parseOutput(it.second, digitMap)
        }

        assertEquals(61229, outputSum)
    }

    @Test
    fun testCharFind() {
        assertEquals(listOf('a'), findUniqueChars("abc", "bc"))
        assertEquals(listOf('d'), findUniqueChars("dab", "ab"))
        assertTrue(containsAll("abcd", "ab"))
        assertFalse(containsAll("acd", "ab"))
    }

    @Test
    fun testDigitMaker() {
        val segmentMap: Map<Int, Char> = buildMap {
            put(0, 'd')
            put(1, 'e')
            put(2, 'a')
            put(3, 'f')
            put(4, 'g')
            put(5, 'b')
            put(6, 'c')
        }

        val digitMap = makeDigitMapFromSegmentMap(segmentMap)

        assertEquals(sortString("abcdeg"), sortString("cagedb"))
        assertEquals(digitMap[sortString("cagedb")], 0)
        assertEquals(digitMap[sortString("ab")], 1)
    }

    @Test
    fun testSearch() {
        val input = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab".split(" ")

        val segmentMap = analyzeInput(input)

    }
}