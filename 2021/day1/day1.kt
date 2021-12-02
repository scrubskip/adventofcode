import java.io.File

fun main() {
    // println(findIncreasesSlidingWindow(listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263),
    // 3))

    println(findIncreasesFold(listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)))

    val lineList = mutableListOf<Int>()

    File("day1.txt").useLines { lines -> lines.forEach { lineList.add(it.toInt()) } }
    println(findIncreasesSlidingWindow(lineList, 3))
}

fun findIncreases(input: List<Int>): Int {
    var increases: Int = 0
    var prevValue: Int? = null
    for (value in input) {
        if (prevValue != null && value > prevValue) {
            increases++
        }
        prevValue = value
    }
    return increases
}

fun findIncreasesFold(input: List<Int>): Int {
    return input.foldIndexed(0) { index, increases, value ->
        if (index > 0 && value > input[index - 1]) increases + 1 else increases
    }
}

fun findIncreasesSlidingWindow(input: List<Int>, windowSize: Int): Int {
    var increases: Int = 0
    var index: Int = windowSize
    var prevWindowValue: Int? = null
    while (index <= input.size) {
        var windowValue: Int = input.slice(index - windowSize..index - 1).sum()
        // println("$index : $windowValue")
        if (prevWindowValue != null && windowValue > prevWindowValue) {
            increases++
        }
        prevWindowValue = windowValue
        index++
    }
    return increases
}
