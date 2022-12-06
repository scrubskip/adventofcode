package day06

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

internal class Day06KtTest {
    @Test
    fun testSample() {
        assertEquals(7, getFirstMarkerIndex("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(5, getFirstMarkerIndex("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(6, getFirstMarkerIndex("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(10, getFirstMarkerIndex("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(11, getFirstMarkerIndex("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }

    @Test
    fun testSampleMessage() {
        assertEquals(19, getMessageIndex("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(23, getMessageIndex("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(23, getMessageIndex("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(29, getMessageIndex("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(26, getMessageIndex("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }
}