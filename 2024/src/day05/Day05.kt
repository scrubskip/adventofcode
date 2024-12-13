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

fun fixOrder(input: List<Int>, rules: Map<Int, Set<Int>>): List<Int> {
    val output = mutableListOf<Int>()
    input.forEach {
        // If this one has to be behind any pages, find that
        val preceders = rules.filterValues { values -> values.contains(it) }
        val highest = preceders.maxOfOrNull { preceder -> output.indexOf(preceder.key) }

        val followers = rules[it]

        // Find the lowest value in the followers list in the output list and then insert this before
        val lowest = followers?.minOf { follower -> output.indexOf(follower) }
        // Add this item to output somewhere between the highest and lowest
        if (highest == -1 && lowest == null) {
            output.add(it)
        } else if (highest != null && highest >= 0) {
            output.add(highest, it)
        } else if (lowest != null && lowest >= 1) {
            output.add(lowest - 1, it)
        } else {
            output.add(it)
        }
    }
    return output
}

fun fixOrder2(numbers: List<Int>, rules: Map<Int, Set<Int>>): List<Int> {
    val orderedList = mutableListOf<Int>()
    val numToBefore = rules.toMutableMap()

    for (num in numbers) {
        var inserted = false
        for (i in 0..orderedList.size) {
            var canInsert = true
            // Check if inserting 'num' at index 'i' violates any rules
            for (j in 0 until i) {
                val existingNum = orderedList[j]
                // Check if 'num' should come before 'existingNum' according to rules
                if (numToBefore.containsKey(num) && numToBefore[num]!!.contains(existingNum)) {
                    canInsert = false
                    break
                }
                // Check if 'existingNum' should come before 'num' according to rules
                if (numToBefore.containsKey(existingNum) && numToBefore[existingNum]!!.contains(num)) {
                    canInsert = false
                    break
                }
            }

            if (canInsert) {
                orderedList.add(i, num)
                inserted = true
                break
            }
        }
        if (!inserted) {
            // If the number cannot be inserted based on the rules,
            // append it to the end (or handle it as an error)
            orderedList.add(num)
        }
    }

    return orderedList
}