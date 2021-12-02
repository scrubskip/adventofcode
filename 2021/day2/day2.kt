import java.io.File

fun main() {
    // println(testSample())

    println(readFile("day2.txt"))
}

fun testSample(): Int {
    val (depth, distance) =
            moveSub(listOf("forward 5", "down 5", "forward 8", "up 3", "down 8", "forward 2"))
    return depth * distance
}

fun readFile(arg: String): Int {
    val lineList = mutableListOf<String>()
    File(arg).useLines { lines -> lines.forEach { lineList.add(it.toString()) } }
    val (depth, distance) = moveSub(lineList)
    return depth * distance
}

fun moveSub(input: List<String>): Pair<Int, Int> {
    var depth: Int = 0
    var distance: Int = 0

    input.forEach {
        // println(it)
        val command = it.split(" ")
        val direction = command[0]
        val unit: Int = command[1].toInt()
        when (direction) {
            "forward" -> distance += unit
            "down" -> depth += unit
            "up" -> depth -= unit
        }
    }

    return Pair(depth, distance)
}
