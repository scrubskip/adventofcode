package day04

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class Day04KtTest {
    @Test
    fun testParse() {
        val schedule = parseSchedule("2-4,6-8")
        assertEquals(2, schedule.first.first)
        assertEquals(4, schedule.first.last)

        assertEquals(6, schedule.second.first)
        assertEquals(8, schedule.second.last)
    }

    @Test
    fun testFullOverlap() {
        assertFalse(isTotalOverlapping(parseSchedule("2-4,6-8")))
        assertTrue(isTotalOverlapping(parseSchedule("2-8,3-7")))
        assertTrue(isTotalOverlapping(parseSchedule("6-6,4-6")))
    }

    @Test
    fun testAnyOverlap() {
        assertFalse(isAnyOverlapping(parseSchedule("2-4,6-8")))
        assertFalse(isAnyOverlapping(parseSchedule("2-3,4-5")))
        assertTrue(isAnyOverlapping(parseSchedule("5-7,7-9")))
        assertTrue(isAnyOverlapping(parseSchedule("2-8,3-7")))
        assertTrue(isAnyOverlapping(parseSchedule("6-6,4-6")))
        assertTrue(isAnyOverlapping(parseSchedule("2-6,4-8")))
    }
}