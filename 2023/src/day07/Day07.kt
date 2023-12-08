package day07

import java.io.File

fun main() {
    val input = File("src/day07/day07.txt").readLines()
    println(getWinnings(input))
}

fun getWinnings(input: List<String>): Int {
    val handBids = input.map { handBid ->
        val parts = handBid.split(" ")
        Pair<Hand, Int>(Hand(parts[0]), parts[1].toInt())
    }
    return handBids.sortedBy { it.first }.mapIndexed { index, pair ->
        (index + 1) * pair.second
    }.sum()
}

enum class HandType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND,
}

enum class CardValue(val stringValue: Char) {
    ONE('1'),
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    TEN('T'),
    JACK('J'),
    QUEEN('Q'),
    KING('K'),
    ACE('A');

    companion object {
        fun getFromChar(char: Char): CardValue {
            return CardValue.values().first { it.stringValue == char }
        }

        fun compareLists(thisList: List<CardValue>, otherList: List<CardValue>): Int {
            if (thisList.size == otherList.size) {
                // Loop through until item doesn't match
                thisList.zip(otherList).forEach {
                    if (it.first != it.second) {
                        return it.first.compareTo(it.second)
                    }
                }
                return 0
            } else {
                throw IllegalArgumentException("Lists are not the same size")
            }
        }
    }
}

class Hand(val input: String) : Comparable<Hand> {
    private val handCharMap: Map<Char, Int> = input.groupingBy { it }.eachCount()
    val handType: HandType = getHandType(handCharMap)

    private fun getHandType(charMap: Map<Char, Int>): HandType {
        return if (charMap.values.any { it == 5 }) {
            HandType.FIVE_OF_A_KIND
        } else if (charMap.values.any { it == 4 }) {
            HandType.FOUR_OF_A_KIND
        } else if (charMap.values.any { it == 3 }) {
            if (charMap.values.any { it == 2 }) {
                HandType.FULL_HOUSE
            } else {
                HandType.THREE_OF_A_KIND
            }
        } else if (charMap.values.filter { it == 2 }.size == 2) {
            HandType.TWO_PAIR
        } else if (charMap.values.filter { it == 2 }.size == 1) {
            HandType.ONE_PAIR
        } else {
            HandType.HIGH_CARD
        }
    }

    override fun compareTo(other: Hand): Int {
        if (handType == other.handType) {
            return CardValue.compareLists(input.map { CardValue.getFromChar(it) },
                other.input.map { CardValue.getFromChar(it) })
        } else {
            return handType.compareTo(other.handType)
        }
    }
}