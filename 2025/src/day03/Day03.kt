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

