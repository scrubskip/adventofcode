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
}