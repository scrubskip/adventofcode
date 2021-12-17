package day16

import java.io.File

fun main() {
    val input = File("src/day16", "day16input.txt").readLines().first()
    val packet = parsePacket(input)
    println(packet.getVersionSum())
    println(packet.getValue())
}

fun String.toBinaryString(): String {
    return this.toCharArray().joinToString("", "", "") {
        it.digitToInt(radix = 16).toString(radix = 2)
            .padStart(4, '0')
    }
}

fun String.fromBinary(): Int {
    return this.toInt(2)
}

fun String.fromBinaryLong(): Long {
    return this.toLong(2)
}

fun parsePacket(hexString: String): Packet {
    return parsePacketWithChildren(hexString.toBinaryString(), 0).first
}

/**
 * Parses a packet from the startIndex
 * @return Pair of the packet at the startIndex and the number of characters consumed
 */
fun parsePacketWithChildren(binaryString: String, startIndex: Int): Pair<Packet, Int> {
    var index = startIndex
    val packet = Packet(
        binaryString.substring(index, index + 3).fromBinary(),
        binaryString.substring(index + 3, index + 6).fromBinary()
    )
    index += 6

    if (packet.isLiteral()) {
        // parse groups
        val numberList = mutableListOf<String>()
        while (true) {
            numberList.add(binaryString.substring(index + 1, index + 5))
            if (binaryString[index] == '0') {
                break
            }
            index += 5
        }
        index += 5
        packet.setNumber(numberList.joinToString(separator = "", postfix = "").fromBinaryLong())

    } else {
        // set the length type id
        packet.setLengthTypeId(binaryString.substring(index, index + 1).fromBinary())
        index += 1
        val length = if (packet.getLengthTypeId() == 0) 15 else 11
        val lengthPackets = binaryString.substring(index, index + length).fromBinary()
        index += length
        val endIndex = if (packet.getLengthTypeId() == 0) index + lengthPackets else lengthPackets
        var counter = if (packet.getLengthTypeId() == 0) index else 0
        while (counter < endIndex) {
            with(parsePacketWithChildren(binaryString, index)) {
                packet.addPacket(first)
                index = second
                counter = if (packet.getLengthTypeId() == 0) index else counter + 1
            }
        }
    }
    return Pair(packet, index)
}

class Packet(val versionCode: Int, val typeId: Int) : Collection<Packet> {
    private val childPackets = mutableListOf<Packet>()

    private var number: Long = 0L

    private var lengthTypeId: Int = 0

    fun addPacket(packet: Packet) {
        childPackets.add(packet)
    }

    fun isLiteral(): Boolean {
        return typeId == 4
    }

    fun setNumber(newNumber: Long) {
        require(isLiteral())
        number = newNumber
    }

    fun getNumber(): Long {
        return number
    }

    fun getValue(): Long {
        return when (typeId) {
            0 -> sumOf { it.getValue() }
            1 -> fold(1) { acc, packet -> acc * packet.getValue() }
            2 -> minOf { it.getValue() }
            3 -> maxOf { it.getValue() }
            4 -> number
            5 -> if (get(0).getValue() > get(1).getValue()) 1 else 0
            6 -> if (get(0).getValue() < get(1).getValue()) 1 else 0
            7 -> if (get(0).getValue() == get(1).getValue()) 1 else 0
            else -> throw Exception("Unknown type id $typeId")
        }
    }

    fun setLengthTypeId(value: Int) {
        lengthTypeId = value
    }

    fun getLengthTypeId(): Int {
        return lengthTypeId
    }

    fun getVersionSum(): Int {
        return versionCode + childPackets.sumOf { it.getVersionSum() }
    }

    override val size: Int
        get() = childPackets.size

    override fun contains(element: Packet): Boolean {
        return childPackets.contains(element)
    }

    override fun containsAll(elements: Collection<Packet>): Boolean {
        return childPackets.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return childPackets.isEmpty()
    }

    override fun iterator(): Iterator<Packet> {
        return childPackets.iterator()
    }

    operator fun get(index: Int): Packet {
        return childPackets[index]
    }
}