package day05

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day05KtTest {

  @Test
  fun testExample() {
    val input = """
      3-5
      10-14
      16-20
      12-18

      1
      5
      8
      11
      17
      32
    """.trimIndent().split("\n")
    assertEquals(3, parseInput(input))
  }

  @Test
  fun testMergeRanges() {
    val input = """
      3-5
      10-14
      16-20
      12-18
    """.trimIndent().split("\n")

    val ranges = mergeRanges(parseRanges(input))
    assertEquals(2, ranges.size)
    assertEquals(3, ranges[0].first)
    assertEquals(5, ranges[0].last)
    assertEquals(10, ranges[1].first)
    assertEquals(20, ranges[1].last)

    assertEquals(14, getTotalCount(ranges))
  }
}