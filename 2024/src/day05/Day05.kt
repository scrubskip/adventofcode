package day05

import java.io.File

fun main() {
    val rules = getRules(File("src/day05/day05rules.txt").readLines())
    val input = File("src/day05/day05updates.txt").readLines()
        .map { update -> update.split(",").map { it.toInt() } }
    println(getMiddles(input, rules).sum())
}

fun getMiddles(input: List<List<Int>>, rules: Map<Int, Set<Int>>): List<Int> {
    return input.filter {
        isPageOrdered(it, rules)
    }.map {
        it[it.size / 2]
    }
}

fun isPageOrdered(input: List<Int>, rules: Map<Int, Set<Int>>): Boolean {
    return input.count {
        val followers = rules[it]
        // Check that the value is either -1 or that the index of this is less than every member of the set
        if (followers == null) {
            true
        } else {
            val index = input.indexOf(it)
            followers.all { follower ->
                val followerIndex = input.indexOf(follower)
                followerIndex == -1 || index < input.indexOf(follower)
            }
        }
    } == input.size
}

fun getRules(input: List<String>): Map<Int, Set<Int>> {
    // Transform input list of type "number|number" to a map of the first number to the second number
    // There might be multiple values
    val rules = mutableMapOf<Int, MutableSet<Int>>()
    input.forEach { rule ->
        val (first, second) = rule.split("|").map { it.toInt() }
        rules.getOrPut(first) { mutableSetOf() }.add(second)
    }
    return rules
}