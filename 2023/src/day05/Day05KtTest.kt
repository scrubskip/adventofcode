package day05

import org.junit.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day05KtTest {
    @Test
    fun testRangeMapper() {
        var rangeMapper = RangeMapper(50, 98, 2)
        assertTrue(rangeMapper.isInRange(98))
        assertTrue(rangeMapper.isInRange(100))
        assertFalse(rangeMapper.isInRange(97))
        assertFalse(rangeMapper.isInRange(101))

        assertEquals(50, rangeMapper.getMappedValue(98))
        assertEquals(51, rangeMapper.getMappedValue(99))
        assertThrows<IllegalArgumentException> { rangeMapper.getMappedValue(97) }

        rangeMapper = RangeMapper(52, 50, 48)
        assertEquals(55, rangeMapper.getMappedValue(53))
    }

    @Test
    fun testMapSegment() {
        val mapSegment = MapSegment()
        mapSegment.addRangeString("50 98 2")
        mapSegment.addRangeString("52 50 48")

        assertEquals(81, mapSegment.getMappedValue(79))
        assertEquals(14, mapSegment.getMappedValue(14))
        assertEquals(57, mapSegment.getMappedValue(55))
        assertEquals(13, mapSegment.getMappedValue(13))
    }

    @Test
    fun testMapSegment2() {
        val mapSegment = MapSegment()
        mapSegment.addRangeString("49 53 8")
        mapSegment.addRangeString("0 11 42")
        mapSegment.addRangeString("42 0 7")
        mapSegment.addRangeString("57 7 4")

        assertEquals(49, mapSegment.getMappedValue(53))
    }

    @Test
    fun testExample() {
        val input = listOf(
            "seeds: 79 14 55 13",
            "",
            "seed-to-soil map:",
            "50 98 2",
            "52 50 48",
            "",
            "soil-to-fertilizer map:",
            "0 15 37",
            "37 52 2",
            "39 0 15",
            "",
            "fertilizer-to-water map:",
            "49 53 8",
            "0 11 42",
            "42 0 7",
            "57 7 4",
            "",
            "water-to-light map:",
            "88 18 7",
            "18 25 70",
            "",
            "light-to-temperature map:",
            "45 77 23",
            "81 45 19",
            "68 64 13",
            "",
            "temperature-to-humidity map:",
            "0 69 1",
            "1 0 69",
            "",
            "humidity-to-location map:",
            "60 56 37",
            "56 93 4"
        )
        val almanac = Almanac(input)

        assertEquals(82, almanac.getMappedValue(79))
        assertEquals(43, almanac.getMappedValue(14))
        assertEquals(86, almanac.getMappedValue(55))
        assertEquals(35, almanac.getMappedValue(13))
    }

}