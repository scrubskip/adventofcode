package day05

import java.io.File

fun main() {
  val input = File("src/day05/day05input.txt").readLines()
  println(parseInput(input))

  println(getTotalCount(mergeRanges(parseRanges(input))))

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

  for (range in input) {
    if (range.isEmpty()) {
      break
    }

    val elements = range.split("-")
    returnList.add(LongRange(elements[0].toLong(), elements[1].toLong()))
  }

  return returnList.toList()
}

fun mergeRanges(ranges : List<LongRange>) : List<LongRange> {
  if (ranges.isEmpty()) {
    return emptyList()
  }

  // Sort ranges by start.
  val sortedRanges = ranges.sortedBy { it.first }
  val mergedRanges = mutableListOf<LongRange>()

  var currentRange = sortedRanges.first()

  for (i in 1 until sortedRanges.size) {
    val nextRange = sortedRanges[i]

    // Check for overlap or adjacency:
    // If the start of the next range is less than or equal to the end of the current range + 1,
    // they overlap or are adjacent.
    if (nextRange.first <= currentRange.last + 1) {
      // Merge: The new end is the maximum of the current end and the next end.
      currentRange = currentRange.first..maxOf(currentRange.last, nextRange.last)
    } else {
      // No overlap/adjacency. Add the currentRange to the merged list.
      mergedRanges.add(currentRange)
      // The nextRange becomes the new candidate for merging.
      currentRange = nextRange
    }
  }

  // Add the last currentRange after the loop finishes.
  mergedRanges.add(currentRange)

  return mergedRanges.toList()
}

fun isSpoiled(ranges: List<LongRange>, ingredient: Long) : Boolean {
  for (range in ranges) {
    if (ingredient in range) {
      return false
    }
  }
  return true
}

fun getTotalCount(ranges : List<LongRange>) : Long {
  return ranges.sumOf { it.last - it.first + 1 }
}