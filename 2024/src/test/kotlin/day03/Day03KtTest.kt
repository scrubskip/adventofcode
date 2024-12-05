package day03

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

class Day03KtTest {

    @Test
    fun testExample() {
        val input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
        assertEquals(161, getMult(input))
    }

    @Test
    fun testDoDontExample() {
        val input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
        assertEquals(48, getMultWithDo(input))
    }
}