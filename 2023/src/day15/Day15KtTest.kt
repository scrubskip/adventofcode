package day15

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class Day15KtTest {
    @Test
    fun testHash() {
        assertEquals(52, getHash("HASH"))
    }

    @Test
    fun testExample() {
        val input = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7".split(",")
        assertEquals(1320, input.sumOf { getHash(it) })
    }
}