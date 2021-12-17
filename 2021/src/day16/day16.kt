package day16

fun main() {

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
        packet.setNumber(numberList.joinToString(separator = "", postfix = "").fromBinary())

    } else {
        // set the length type id
        packet.setLengthTypeId(binaryString.substring(index, index + 1).fromBinary())
        index += 1
        val length = when (packet.getLengthTypeId()) {
            0 -> 15
            1 -> 11
            else -> throw Exception("Not valid length packet")
        }
        val lengthPackets = binaryString.substring(index, index + length).fromBinary()
        index += length
        val endIndex = index + lengthPackets
        while (index < endIndex) {
            with(parsePacketWithChildren(binaryString, index)) {
                packet.addPacket(first)
                index = second
            }
        }
    }

    return Pair(packet, index)
}

class Packet(val versionCode: Int, val typeId: Int) {
    private val childPackets = mutableListOf<Packet>()

    private var number: Int = 0

    private var lengthTypeId: Int = 0

    fun addPacket(packet: Packet) {
        childPackets.add(packet)
    }

    fun getPackets(): List<Packet> {
        return childPackets.toList()
    }

    fun isLiteral(): Boolean {
        return typeId == 4
    }

    fun setNumber(newNumber: Int) {
        require(isLiteral())
        number = newNumber
    }

    fun getNumber(): Int {
        return number
    }

    fun setLengthTypeId(value: Int) {
        lengthTypeId = value
    }

    fun getLengthTypeId(): Int {
        return lengthTypeId
    }
}