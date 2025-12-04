package day03

import java.io.File

fun main() {
  val input = File("src/day03/day03input.txt").readLines()
  println(input.sumOf { getJoltage(it)})
}

fun getJoltage(batteries : String) : Int {
  // Get the first character
  val firstIndexOfMax = batteries.take(batteries.length - 1).indices
    .maxBy { batteries[it].digitToInt() }
  // println("$batteries $firstIndexOfMax")
  // println(batteries.substring(firstIndexOfMax + 1, batteries.length))
  val restOfBattery = batteries.substring(firstIndexOfMax + 1, batteries.length)
  val nextIndex = restOfBattery.indices
    .maxBy { restOfBattery[it].digitToInt() } + firstIndexOfMax + 1
  // println("$nextIndex")
  return batteries[firstIndexOfMax].digitToInt() * 10 + batteries[nextIndex].digitToInt()
}

fun getJoltageWithWindow(batteries : String, joltageCount: Int) : Long {
  var returnString = ""
  var onIndices = mutableSetOf<Int>()
  var windowString = ""
  var windowIndex = 0

  // When we have space, take the first joltage items and look for the max
  while (onIndices.size < joltageCount) {
    windowString = batteries.substring(windowIndex,
                                       (windowIndex + joltageCount)
                                         .coerceAtMost(batteries.length)
    )
    println("$windowString")
    val maxIndexInWindow = windowString.indices
      .maxBy { if (!onIndices.contains(it + windowIndex)) windowString[it].digitToInt() else -1 }
    val adjustedIndex = maxIndexInWindow + windowIndex
    onIndices.add(adjustedIndex)
    println("adding ${batteries[adjustedIndex]} at ${adjustedIndex}")
    if (windowIndex + joltageCount < batteries.length) {
      windowIndex++
    }
  }

  return batteries.filterIndexed { index, char -> onIndices.contains(index)}.toLong()
}

