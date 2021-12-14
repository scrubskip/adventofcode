package day14

import java.io.File

fun main() {
    val data = parseInput(File("src/day14", "day14input.txt").readLines())
    var chain = data.first
    var rules = data.second

    for (i in 1..10) {
        chain = runStep(chain, rules)
    }
    println(getQuantityDifference(chain))

    val countMap = runStepExtended(data.first, data.second, 40)
    with(countMap.values.sortedByDescending { it }) {
        println(first() - last())
    }
}

fun parseInput(input: List<String>): Pair<String, Map<String, Char>> {
    with(input.filter { it.isNotBlank() }
        .partition { !it.contains(" -> ") }) {

        return Pair(first.first(),
            buildMap {
                second.forEach {
                    with(it.split(" -> ")) {
                        put(get(0), get(1).toCharArray().first())
                    }
                }
            })
    }
}

fun runStep(chain: String, rules: Map<String, Char>): String {
    val insertionMap = mutableMapOf<Int, Char>()

    for ((index, window) in chain.windowed(2).withIndex()) {
        if (rules.contains(window)) {
            insertionMap[index + 1] = rules!![window] as Char
        }
    }
    // Now create a new string with the characters inserted
    val mutableChain = chain.toMutableList()
    for ((insertOffset, insertIndex) in insertionMap.keys.withIndex()) {
        insertionMap[insertIndex]?.let { mutableChain.add(insertIndex + insertOffset, it) }
    }
    return mutableChain.joinToString(separator = "", postfix = "")
}

fun getQuantityDifference(chain: String): Long {
    val grouped =
        chain.groupingBy { it }.fold(0L) { acc, _ -> acc + 1 }
            .entries.sortedByDescending { it.value }

    return grouped.first().value - grouped.last().value
}

/**
 * Runs the number of steps with the rules
 * @return Map of character to frequency
 */
fun runStepExtended(chain: String, rules: Map<String, Char>, steps: Int): Map<Char, Long> {
    val returnMap = chain.groupingBy { it }.fold(0L) { acc, _ -> acc + 1 }.toMutableMap()

    var pairCount = chain.windowed(2).groupingBy { it }.fold(0L) { acc, _ -> acc + 1 }.toMutableMap()

    (1..steps).forEach { _ ->
        val newPairCount = mutableMapOf<String, Long>()
        for (entry in pairCount.entries) {
            if (rules.contains(entry.key)) {
                val char = rules[entry.key]!!
                returnMap[char] = returnMap.getOrPut(char) { 0L } + (entry.value)
                for (newEntry in getEntries(char, entry.key)) {
                    newPairCount[newEntry] = newPairCount.getOrDefault(newEntry, 0L) + entry.value
                }
            } else {
                newPairCount[entry.key] = newPairCount.getOrDefault(entry.key, 0L) + entry.value
            }
        }
        pairCount = newPairCount
    }

    return returnMap
}

fun getEntries(char: Char, pair: String): List<String> {
    return listOf(pair.substring(0, 1) + char, char + pair.substring(1, 2))
}