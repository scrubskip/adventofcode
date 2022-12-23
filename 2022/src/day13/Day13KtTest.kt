package day13

import org.junit.jupiter.api.Assertions.*
import java.io.File
import kotlin.test.Test

internal class Day13KtTest {
    @Test
    fun testParse() {
        val simpleList = parseInput("[1,1,3,1,1]")
        assertEquals(5, simpleList.size)
        assertEquals(1, simpleList[0])
        assertEquals(3, simpleList[2])
    }
    @Test
    fun testNestedParse() {
        val nestedList = parseInput("[[1],[2,3,4]]")
        assertEquals(2, nestedList.size)
        assertEquals(1, (nestedList[0] as List<Any>)[0])
        assertEquals(2, (nestedList[1] as List<Any>)[0])
    }

    @Test
    fun testCompare() {
        var left = parseInput("[1,1,3,1,1]")
        var right = parseInput("[1,1,5,1,1]")
        assertEquals(-1, compareLists(left, right))
    }

    @Test
    fun testNestedCompare() {
        var left = parseInput("[[1],[2,3,4]]")
        var right = parseInput("[[1],4]")
        assertEquals(-1, compareLists(left, right))

        left = parseInput("[9]")
        right = parseInput("[[8,7,6]]")
        assertEquals(1, compareLists(left, right))
    }

    @Test
    fun testRunOut() {
        var left = parseInput("[[4,4],4,4]")
        var right = parseInput("[[4,4],4,4,4]")
        assertEquals(-1, compareLists(left, right))

        left = parseInput("[7,7,7,7]")
        right = parseInput("[7,7,7]")
        assertEquals(1, compareLists(left, right))

        left = parseInput("[]")
        right = parseInput("[3]")
        assertEquals(-1, compareLists(left, right))

        left = parseInput("[[[]]]")
        right = parseInput("[[]]")
        assertEquals(1, compareLists(left, right))
    }

    @Test
    fun testComplex() {
        var left = parseInput("[1,[2,[3,[4,[5,6,7]]]],8,9]")
        var right = parseInput("[1,[2,[3,[4,[5,6,0]]]],8,9]")
        assertEquals(1, compareLists(left, right))
    }

    @Test
    fun testSample() {
        assertEquals(13, runFileInput("src/day13/day13sample.txt"))
    }
}