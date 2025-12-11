package day05

import java.io.File

fun main() {
  val input = File("src/day05/day05input.txt").readLines()
  println(parseInput(input))
}

fun parseInput(input: List<String>) : Int {
  var parseRange = true
  val ranges = mutableListOf<LongRange>()
  val ingredients = mutableListOf<Long>()
  input.forEach {
    if (it.isEmpty()) {
      parseRange = false
    } else if (parseRange) {
      val elements = it.split("-")
      ranges.add(LongRange(elements[0].toLong(), elements[1].toLong()))
    } else {
      ingredients.add(it.toLong())
    }
  }

  return ingredients.filterNot { isSpoiled(ranges, it) }.size
}

fun parseRanges(input: List<String>) : List<LongRange> {
  val returnList = mutableListOf<LongRange>()

  input.forEach {
    val elements = it.split("-")
    returnList.add(LongRange(elements[0].toLong(), elements[1].toLong()))
  }

  return returnList.toList()
}

fun isSpoiled(ranges: List<LongRange>, ingredient: Long) : Boolean {
  for (range in ranges) {
    if (ingredient in range) {
      return false
    }
  }
  return true
}