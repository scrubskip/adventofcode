package day01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day01KtTest {

    @Test
    fun testExample() {
        val list1 = listOf("L68",
            "L30",
                "R48",
                "L5",
                "R60",
                "L55",
                "L1",
                "L99",
                "R14",
                "L82")

        assertEquals(3, getPassword(list1))

        assertEquals(6, getPassword(list1, true))
    }

    @Test
    fun testRollover() {
        assertEquals(10, getPassword(listOf("L1000"), true))
    }

}