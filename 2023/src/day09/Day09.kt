package day09

import java.io.File

fun main() {
    val input = File("src/day09/day09.txt").readLines()
    println(getSumOfNext(input))
}

fun getSumOfNext(input: List<String>) =
    input.map { it.split(" ").map { value -> value.toLong() } }
        .sumOf { intList -> getNextInSequence(intList) }

fun getNextInSequence(input: List<Long>): Long {
    // First get differences
    var lists = mutableListOf<MutableList<Long>>(input.toMutableList())

    var currentList = lists.first()
    var differenceList = currentList.drop(1).mapIndexed { index: Int, i: Long ->
        i - currentList[index]
    }
    while (differenceList.any { it != 0L }) {
        currentList = differenceList.toMutableList()
        lists.add(currentList)
        differenceList = currentList.drop(1).mapIndexed { index: Int, i: Long ->
            i - currentList[index]
        }
    }
    lists.add(differenceList.toMutableList())
    // OK, now, let's extrapolate
    while (lists.size > 1) {
        differenceList = lists.last()
        currentList = lists[lists.size - 2]
        currentList.add(currentList.last() + differenceList.last())
        // now pop
        lists.remove(differenceList)
    }
    return currentList.last()
}