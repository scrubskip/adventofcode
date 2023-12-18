package day16

fun main() {

}

data class Coordinate(val x: Int, val y: Int)
class CaveMap(input: List<String>) {
    private val tiles: Map<Coordinate, Char>

    init {
        val inputTiles = mutableMapOf<Coordinate, Char>()
        val energizedTiles = mutableSetOf<Coordinate>()

        // Parse each line and add chars to tile
        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, character ->
                inputTiles[Coordinate(rowIndex, colIndex)] = character
            }
        }
        tiles = inputTiles.toMap()
    }

    /**
     * Runs beam and energizes tiles.
     */
    fun runBeam(startCoordinate: Coordinate, deltaX: Int, deltaY: Int) {

    }
}