package day02

import java.io.File

fun main() {
  val input = File("src/day02/day02input.txt").readLines()
  println(getInvalidIdSum(input[0]))
  println(getInvalidIdSum(input[0], ::isInvalidIdMultiple))
}

fun getInvalidIdSum(input :String, filterFunction : (Long) -> Boolean = ::isInvalidId) : Long {
  return getRanges(input).map {
    it.fold(0L) {acc, i -> if (filterFunction(i)) {acc + i} else {acc}}}
    .reduce { acc, i -> acc + i}
}
fun getRanges(input : String) : List<LongRange> {
  return input.split(",")
      .map { val items = it.split("-")
        LongRange( items[0].toLong(), items[1].toLong())
      }
}

fun isInvalidId(id : Long) : Boolean {
  val idString = id.toString()
  val firstPart = idString.take(idString.length / 2)
  val secondPart = idString.substring(idString.length / 2)

  return firstPart.compareTo(secondPart) == 0
}

fun isInvalidIdMultiple(id: Long) : Boolean {
  val idString = id.toString()
  for (sequenceLength in 1.. idString.length / 2) {
    if (idString.chunked(sequenceLength).toHashSet().size == 1) {
      return true
    }
  }
  return false
}