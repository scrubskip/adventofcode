import java.io.File
import kotlin.collections.mutableMapOf

fun main() {
    // println(testDay3Sample())
    println(readInput("day3.txt"))
}

fun testDay3Sample(): Int {
    var (gamma, epsilon) =
            calulatePowerConsumption(
                    listOf(
                            "00100",
                            "11110",
                            "10110",
                            "10111",
                            "10101",
                            "01111",
                            "00111",
                            "11100",
                            "10000",
                            "11001",
                            "00010",
                            "01010"
                    )
            )
    return gamma * epsilon
}

fun readInput(arg: String): Int {
    var (gamma, epsilon) = calulatePowerConsumption(File(arg).readLines())
    return gamma * epsilon
}

fun calulatePowerConsumption(input: List<String>): Pair<Int, Int> {

    // Map of index to number of "1"s in the input.
    var onesFrequencyMap: MutableMap<Int, Int> = mutableMapOf()
    var maxSize: Int = 0

    input.forEach {
        maxSize = Math.max(it.length, maxSize)
        it.forEachIndexed { index, value ->
            if ("1".single().equals(value)) {
                onesFrequencyMap[index] = onesFrequencyMap.getOrDefault(index, 0) + 1
            }
        }
    }

    // Now loop through the entries in the map to calculate gamma and epsilon
    var gammaString: String = ""
    var epsilonString: String = ""
    val commonSize = (input.size / 2)
    for (index in 0..maxSize - 1) {
        if (onesFrequencyMap.getOrDefault(index, 0) >= commonSize) {
            gammaString += "1"
            epsilonString += "0"
        } else {
            gammaString += "0"
            epsilonString += "1"
        }
    }
    println("gamma $gammaString epsilon $epsilonString")

    return Pair(gammaString.toInt(2), epsilonString.toInt(2))
}
