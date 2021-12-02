import java.io.File

fun main() {
    // println(findIncreases(arrayOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)))
    val lineList = mutableListOf<Int>()

    File("day1.txt").useLines { lines -> lines.forEach { lineList.add(it.toInt()) } }
    println(findIncreases(lineList.toTypedArray()))
}

fun findIncreases(input: Array<Int>): Int {
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
