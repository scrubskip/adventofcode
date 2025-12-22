package day06

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day06KtTest {

  @Test
  fun testParse() {
    val input = """
      123 328  51 64 
       45 64  387 23 
        6 98  215 314
      *   +   *   + 
    """.trimIndent().split("\n")

    val output = parseInput(input)
    println(output)
    assertEquals(4, output.size)
    assertEquals(4277556, calculate(output))
  }
}