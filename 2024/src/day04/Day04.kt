package day04

fun main() {

}

fun countStringInGrid(grid: List<String>, target: String): Int {
    val rows = grid.size
    val cols = grid[0].length

    fun isSafe(row: Int, col: Int): Boolean {
        return row in 0..<rows && col >= 0 && col < cols
    }

    fun search(row: Int, col: Int, index: Int, dx: Int = 0, dy: Int = 0): Boolean {
        if (index == target.length) {
            return true
        }

        if (!isSafe(row, col) || grid[row][col] != target[index]) {
            return false
        }

        // Explore all 8 directions only for the first character
        val directions = if (index == 0) {
            listOf(
                0 to 1, 0 to -1, 1 to 0, -1 to 0, // Horizontal and vertical
                1 to 1, 1 to -1, -1 to 1, -1 to -1 // Diagonals
            )
        } else {
            listOf(Pair(dx, dy)) // Continue in the same direction
        }

        for ((currDx, currDy) in directions) {
            if (search(row + currDx, col + currDy, index + 1, currDx, currDy)) {
                return true
            }
        }

        return false
    }
    var result = 0
    for (row in 0 until rows) {
        for (col in 0 until cols) {
            if (search(row, col, 0)) {
                result++
            }
        }
    }

    return result
}