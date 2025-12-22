package day06

import java.io.File

fun main() {
  val input = File("src/day06/day06input.txt").readLines()
  println(calculate(parseInput(input)))
}

// Parses a list of row data into lists of column data
fun parseInput(input : List<String>) : List<List<String>> {
  val returnLists = mutableListOf<MutableList<String>>()
  val whitespace = "\\s+".toRegex()
  for (line in input) {
    var column = 0
    for (element in line.split(whitespace)) {
      val cleanedElement = element.trim()
      if (cleanedElement.isEmpty()) {
        continue
      }
      var list = if (returnLists.size > column) {
        returnLists[column]
      } else {
        returnLists.add(column, mutableListOf())
        returnLists[column]
      }

      list.add(cleanedElement)
      column++
    }
  }

  return returnLists.toList()
}

fun calculate(homework : List<List<String>>) : Long {
  val maxCol = homework.size
  val maxRows = homework[0].size
  var result = 0L
  for (column in 0..<maxCol) {
    val operand = homework[column][maxRows - 1]
    if (operand == "*") {
      result += homework[column].take(maxRows - 1).fold(1L, { acc, element ->
        acc * element.toLong()
      })
    } else if (operand == "+") {
      result += homework[column].take(maxRows - 1).fold(0L, { acc, element ->
        acc + element.toLong()
      })
    }
  }
  return result
}