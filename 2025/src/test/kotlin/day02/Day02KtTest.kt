package day02

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day02KtTest {

  @Test
  fun testExample() {
    val input = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
      "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
      "824824821-824824827,2121212118-2121212124"
    val output = getInvalidIdSum(input)
    assertEquals(1227775554, output)
    assertEquals(4174379265, getInvalidIdSum(input, ::isInvalidIdMultiple))
  }

  @Test
  fun isInvalidId() {
    assertTrue(isInvalidId(11))
    assertTrue(isInvalidId(22))
    assertTrue(isInvalidId(1212))
    assertTrue(isInvalidId(1010))
    assertFalse(isInvalidId(1))
    assertFalse(isInvalidId(12))
    assertFalse(isInvalidId(123))
    assertFalse(isInvalidId(1234))
    assertFalse(isInvalidId(100))
  }

  @Test
  fun isInvalidIdMultiple() {
    assertTrue(isInvalidIdMultiple(222222))
    assertTrue(isInvalidIdMultiple(1188511885))
    assertTrue(isInvalidIdMultiple(11))
    assertFalse(isInvalidIdMultiple(100))
    assertFalse(isInvalidIdMultiple(10))

  }
}