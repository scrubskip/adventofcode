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

}