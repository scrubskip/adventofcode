package day04

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day04KtTest {

  @Test
  fun testExample() {
    val input = """
      ..@@.@@@@.
      @@@.@.@.@@
      @@@@@.@.@@
      @.@@@@..@.
      @@.@@@@.@@
      .@@@@@@@.@
      .@.@.@.@@@
      @.@@@.@@@@
      .@@@@@@@@.
      @.@.@@@.@.
    """.trimIndent().split("\n")

    val floorPlan = FloorPlan(input)
    assertEquals(13, floorPlan.findAccessible().size)
  }

}