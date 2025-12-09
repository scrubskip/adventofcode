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

  @Test
  fun testRemoveCell() {
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
    val coordinate = Coordinate(0, 2)
    assertEquals(floorPlan.ROLL, floorPlan.getItemAtCoordinate(coordinate))
    floorPlan.removeCell(coordinate)
    assertEquals(floorPlan.EMPTY, floorPlan.getItemAtCoordinate(coordinate))
  }

  @Test
  fun testRemoveAll() {
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
    assertEquals(43, floorPlan.removeAllAccessible())
  }


}