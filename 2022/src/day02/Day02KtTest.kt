package day02

import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class Day02KtTest {

    @Test
    fun testCalculateScore() {
        assertEquals(8, calculateScore("A Y"))
        assertEquals(1, calculateScore("B X"))
        assertEquals(6, calculateScore("C Z"))
    }
}