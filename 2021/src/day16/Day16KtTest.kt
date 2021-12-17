package day16

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class Day16KtTest {
    @Test
    fun testUtilities() {
        assertEquals("1010", "A".toBinaryString())
        assertEquals("110100101111111000101000" , "D2FE28".toBinaryString())

        assertEquals("00111000000000000110111101000101001010010001001000000000",
            "38006F45291200".toBinaryString())

        assertEquals(3, "011".fromBinary())
    }

    @Test
    fun testExample() {
        val packet = parsePacket("D2FE28")
        assertEquals(6, packet.versionCode)
        assertEquals(4, packet.typeId)
        assertEquals(2021, packet.getNumber())
    }

    @Test
    fun testNestedExample() {
        val packet = parsePacket("38006F45291200")
        assertEquals(1, packet.versionCode)
        assertEquals(6, packet.typeId)
        assertEquals(2, packet.getPackets().size)

    }
}