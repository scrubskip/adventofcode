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
        chain.groupingBy { it }.fold(0.toLong()) { acc, _ -> acc + 1 }
            .entries.sortedByDescending { it.value }

    return grouped.first().value - grouped.last().value
}