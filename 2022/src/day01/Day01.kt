package day01

import java.io.File

fun main() {
    val input = File("src/day01/Day01.txt").readLines()
    val list = getCalorieCounts(input).sortedDescending();
    println("Max calories = ${list.first()}")
    println("Max calories with backup = ${getMaxWithBackup(list)}")
}

/**
 * {@return} sum of top 3 items (assuming sorted descending)
 */
fun getMaxWithBackup(input : List<Int>) : Int {
    return input.take(3).sum()
}

/**
 * {@return} sorted descending list of calorie counts
 */
fun getCalorieCounts(input : List<String>) : List<Int> {
    var returnList =  mutableListOf<Int>()
    var currentValue = 0
    input.forEach {
        val cal = it.toIntOrNull()
        if (cal != null) {
            currentValue += cal
        } else {
            returnList.add(currentValue)
            currentValue = 0
        }
    }

    returnList.add(currentValue)

    return returnList.sortedDescending()
}