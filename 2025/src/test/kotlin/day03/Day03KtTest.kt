package day03

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day03KtTest {
  @Test
  fun getJoltage() {
    assertEquals(98, getJoltage("987654321111111"))
    assertEquals(89, getJoltage("811111111111119"))
    assertEquals(78, getJoltage("234234234234278"))
    assertEquals(92, getJoltage("818181911112111"))
  }

  @Test
  fun getJoltageWithWindow() {
    assertEquals(987654321111, getJoltageWithWindow("987654321111111", 12))
    assertEquals(811111111119, getJoltageWithWindow("811111111111119", 12))
    assertEquals(434234234278, getJoltageWithWindow("234234234234278", 12))
      assertEquals(888911112111, getJoltageWithWindow("818181911112111", 12))
  }

}