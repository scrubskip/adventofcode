package day16

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class Day16KtTest {
    @Test
    fun testUtilities() {
        assertEquals("1010", "A".toBinaryString())
        assertEquals("110100101111111000101000", "D2FE28".toBinaryString())

        assertEquals(
            "00111000000000000110111101000101001010010001001000000000",
            "38006F45291200".toBinaryString()
        )

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
        with(packet) {
            assertEquals(1, versionCode)
            assertEquals(6, typeId)
            assertEquals(2, size)

            assertEquals(10, this[0].getNumber().toInt())
            assertEquals(20, this[1].getNumber().toInt())

        }
        val packet2 = parsePacket("EE00D40C823060")
        with(packet2) {
            assertEquals(7, versionCode)
            assertEquals(3, typeId)
            assertEquals(3, size)

            assertEquals(1, this[0].getNumber().toInt())
            assertEquals(2, this[1].getNumber().toInt())
            assertEquals(3, this[2].getNumber().toInt())
        }

        with(parsePacket("8A004A801A8002F478")) {
            assertEquals(16, getVersionSum())
        }

        with(parsePacket("620080001611562C8802118E34")) {
            assertEquals(12, getVersionSum())
        }

        with(parsePacket("C0015000016115A2E0802F182340")) {
            assertEquals(23, getVersionSum())
        }

        with(parsePacket("A0016C880162017C3686B18A3D4780")) {
            assertEquals(31, getVersionSum())
        }
    }
}