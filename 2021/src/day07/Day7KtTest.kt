package day07

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class Day7KtTest {
    @Test
    fun testExample() {
        val input = listOf(16,1,2,0,4,2,7,1,2,14)
        assertEquals(37, calculateFuel(input, 2))
        assertEquals(41, calculateFuel(input, 1))

        assertEquals(37, findLowestFuel(input).first)
        assertEquals(2, findLowestFuel(input).second)
    }

    @Test
    fun testLinearExample() {
        val input = listOf(16,1,2,0,4,2,7,1,2,14)
        assertEquals(168, calculateFuelLinear(input, 5))
        assertEquals(206, calculateFuelLinear(input, 2))

        assertEquals(168, findLowestFuel(input, ::calculateFuelLinear).first)
        assertEquals(5, findLowestFuel(input, ::calculateFuelLinear).second)
    }
}