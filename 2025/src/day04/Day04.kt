package day04

import java.io.File

fun main() {
  val input = File("src/day04/day04input.txt").readLines()
  val floorPlan = FloorPlan(input)

  println(floorPlan.findAccessible().size)

  val floorPlan2 = FloorPlan(input)
  println(floorPlan2.removeAllAccessible())
}

data class Coordinate(val x: Int, val y: Int)

class FloorPlan(val floor : List<String>) {
  val _floor = floor.toMutableList()
  val rows = floor.size
  val cols = floor.first().length

  val EMPTY : Char = '.'
  val ROLL : Char = '@'

  fun findAccessible() : Set<Coordinate> {
    val returnList = mutableSetOf<Coordinate>()

    for (j in (0..<rows)) {
      for (i in (0..<cols)) {
        val coordinate = Coordinate(i, j)
        if (getItemAtCoordinate(coordinate) == ROLL) {
          val neighbors = getNeighbors(coordinate)
          if (neighbors.filter { getItemAtCoordinate(it) == ROLL }.size < 4) {
            returnList.add(coordinate)
          }
        }
      }
    }
    return returnList
  }

  fun removeCell(coordinate: Coordinate) {
    _floor[coordinate.y] = _floor[coordinate.y].replaceRange(coordinate.x,coordinate.x+1,
                                     EMPTY.toString())
  }

  fun getItemAtCoordinate(coordinate: Coordinate) : Char {
    return _floor[coordinate.y][(coordinate.x)]
  }

  fun getNeighbors(coordinate: Coordinate) : List<Coordinate> {
    val returnList = mutableListOf<Coordinate>()
    for (i in coordinate.x - 1..coordinate.x + 1) {
      for (j in coordinate.y - 1 .. coordinate.y + 1) {
        if (i in 0..<cols && j in 0..<rows && (i != coordinate.x || j != coordinate.y)) {
          returnList.add(Coordinate(i, j))
        }
      }
    }
    return returnList.toList()
  }

  fun removeAllAccessible() : Int {
    var removeCount = 0
    while (true) {
      val accessible = findAccessible()
      accessible.forEach { removeCell(it) }

      if (accessible.isEmpty()) {
        break
      } else {
        removeCount+= accessible.size
      }
    }
    return removeCount
  }


}