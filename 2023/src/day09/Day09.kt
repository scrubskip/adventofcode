package day09

import java.io.File

fun main() {
    val input = File("src/day09/day09.txt").readLines()
    println(getSumOfSequence(input))
    println(getSumOfSequence(input, false))
}

fun getSumOfSequence(input: List<String>, next: Boolean = true) =
    input.map { it.split(" ").map { value -> value.toLong() } }
        .sumOf { intList -> getInSequence(intList, next) }

fun getInSequence(input: List<Long>, next: Boolean = true): Long {
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
        if (next) {
            currentList.add(currentList.last() + differenceList.last())
        } else {
            currentList.add(0, currentList.first() - differenceList.first())
        }
        // now pop
        lists.remove(differenceList)
    }
    return if (next) {
        currentList.last()
    } else {
        currentList.first()
    }
}