package day01

import kotlin.test.Test
import org.junit.jupiter.api.Assertions.*

internal class Day01KtTest {
    @Test
    fun testExample() {
        val input = listOf(
            "1000",
            "2000",
            "3000",
            "",
            "4000",
            "",
            "5000",
            "6000",
            "",
            "7000",
            "8000",
            "9000",
            "",
            "10000"
        )
        val calorieCounts = getCalorieCounts(input).sortedDescending()
        assertEquals(5, calorieCounts.size)
        assertEquals(24000, calorieCounts.first())
        assertEquals(45000, getMaxWithBackup(calorieCounts))
    }
}