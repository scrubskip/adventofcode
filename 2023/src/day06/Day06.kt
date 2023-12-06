package day06

fun main() {
    val timeToDistance = listOf(
        Pair(59L, 543L),
        Pair(68L, 1020L),
        Pair(82L, 1664L),
        Pair(74L, 1022L)
    )
    println(getMarginOfError(timeToDistance))

    println(getMarginOfError(listOf(Pair(59688274, 543102016641022))))
}

fun getMarginOfError(input: List<Pair<Long, Long>>): Long {
    return input.map {
        val range = getWinRange(it.first, it.second)
        range.last - range.first + 1
    }.reduce { acc, i ->
        acc * i
    }
}

fun getWinRange(time: Long, distance: Long): LongRange {
    var startIndex: Long = (1..time).first {
        getTotalDistance(it, time) > distance
    }
    var endIndex: Long = (1 until time).reversed().first {
        getTotalDistance(it, time) > distance
    }
    // println("$time $distance: $startIndex to $endIndex")
    return LongRange(startIndex, endIndex)
}

fun getTotalDistance(buttonTime: Long, totalTime: Long): Long {
    return buttonTime * (totalTime - buttonTime)
}