package day06

fun main() {
    val timeToDistance = listOf(
        Pair(59, 543),
        Pair(68, 1020),
        Pair(82, 1664),
        Pair(74, 1022)
    )
    println(getMarginOfError(timeToDistance))
}

fun getMarginOfError(input: List<Pair<Int, Int>>): Int {
    return input.map {
        val range = getWinRange(it.first, it.second)
        range.last - range.first + 1
    }.reduce { acc, i ->
        acc * i
    }
}

fun getWinRange(time: Int, distance: Int): IntRange {
    var startIndex: Int = (1..time).first {
        getTotalDistance(it, time) > distance
    }
    var endIndex: Int = (1 until time).reversed().first {
        getTotalDistance(it, time) > distance
    }
    // println("$time $distance: $startIndex to $endIndex")
    return IntRange(startIndex, endIndex)
}

fun getTotalDistance(buttonTime: Int, totalTime: Int): Int {
    return buttonTime * (totalTime - buttonTime)
}