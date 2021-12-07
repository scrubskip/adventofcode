package day06

import java.io.File

fun main() {
    var fish = File("src/day06", "day6input.txt").readLines().single().split(",").map{it.toInt()}

    println(runList(fish, 256))
}

fun runStep(fish : List<Int>) : List<Int> {
    return fish.map {if (it > 0) it - 1 else 6} + List(fish.count { it == 0 }) { 8 }
}

fun runList(fish : List<Int>, days: Int) : Long {
    val groups = fish.groupingBy { it }.fold(
        0
    ) { accumulator: Long, _: Int -> accumulator + 1 }.toMutableMap()


    for (i in 1..days) {
        for (countIndex in 0..8) {
            groups[countIndex - 1] = groups.getOrDefault(countIndex, 0)
        }
        // All the -1s become 6s and create 8s
        groups[8] = groups.getOrDefault(-1, 0) // overwrite old 8s
        groups[6] = groups.getOrDefault(6, 0) + groups.getOrDefault(-1, 0)
        groups[-1] = 0
    }

    return groups.values.sumOf { it }
}