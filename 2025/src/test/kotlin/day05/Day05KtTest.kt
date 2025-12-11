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
}