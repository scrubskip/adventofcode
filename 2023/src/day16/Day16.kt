package day16

import java.io.File
import kotlin.math.max

fun main() {
    val input = File("src/day16/day16.txt").readLines()
    val caveMap = CaveMap(input)

    println("Cave map size ${caveMap.getMaxWidth()}, ${caveMap.getMaxHeight()}")
    caveMap.startBeam()

    println(caveMap.energizedTiles.size)
    println(caveMap.findMax())
}

data class Coordinate(val x: Int, val y: Int)

data class Beam(private val initial: Coordinate, var deltaX: Int, var deltaY: Int) {
    val startCoordinate = Coordinate(initial.x, initial.y)

    fun updatePosition(position: Coordinate) {
        // println("{$startCoordinate ($deltaX, $deltaY): $position ")
    }
}

class CaveMap(input: List<String>) {
    private val tiles: Map<Coordinate, Char>
    private val seenBeams = HashSet<String>()
    private val beamQueue = mutableListOf<Beam>()
    private var maxWidth = 0
    private var maxHeight = 0
    val energizedTiles = HashSet<Coordinate>()

    init {
        val inputTiles = mutableMapOf<Coordinate, Char>()

        // Parse each line and add chars to tile
        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, character ->
                inputTiles[Coordinate(colIndex, rowIndex)] = character
                if (colIndex > maxWidth) {
                    maxWidth = colIndex
                }
            }
            if (rowIndex > maxHeight) {
                maxHeight = rowIndex
            }
        }
        tiles = inputTiles.toMap()
    }

    fun findMax(): Int {
        var maxTiles = 0
        (0..maxWidth).forEach { col ->
            // Look down from top and down from bottom
            startBeam(Beam(Coordinate(col, 0), 0, 1))
            maxTiles = max(energizedTiles.size, maxTiles)
            startBeam(Beam(Coordinate(col, maxHeight), 0, -1))
            maxTiles = max(energizedTiles.size, maxTiles)
        }
        (0..maxHeight).forEach { row ->
            startBeam(Beam(Coordinate(0, row), 1, 0))
            maxTiles = max(energizedTiles.size, maxTiles)
            startBeam(Beam(Coordinate(maxWidth, row), -1, 0))
            maxTiles = max(energizedTiles.size, maxTiles)
        }
        return maxTiles
    }

    /**
     * Clears everything except the tiles between runs
     */
    fun reset() {
        beamQueue.clear()
        seenBeams.clear()
        energizedTiles.clear()
    }

    fun startBeam(startingBeam: Beam = Beam(Coordinate(0, 0), 1, 0)) {
        reset()
        addBeam(startingBeam)

        while (beamQueue.isNotEmpty()) {
            runBeam(beamQueue.removeAt(0))
        }
    }

    fun getMaxWidth(): Int {
        return maxWidth
    }

    fun getMaxHeight(): Int {
        return maxHeight
    }

    /**
     * Runs beam and energizes tiles.
     */
    fun runBeam(beam: Beam) {
        var position = beam.startCoordinate
        var running = true
        while (running) {
            beam.updatePosition(position)
            energizedTiles.add(position)

            val tile = tiles[position]
            if (tile == '/' || tile == '\\') {
                // mirror
                if (beam.deltaX == 1) {
                    beam.deltaX = 0
                    beam.deltaY = if (tile == '/') -1 else 1
                } else if (beam.deltaX == -1) {
                    beam.deltaX = 0
                    beam.deltaY = if (tile == '/') 1 else -1
                } else if (beam.deltaY == 1) {
                    beam.deltaY = 0
                    beam.deltaX = if (tile == '/') -1 else 1
                } else if (beam.deltaY == -1) {
                    beam.deltaY = 0
                    beam.deltaX = if (tile == '/') 1 else -1
                }
            } else if (beam.deltaX != 0 && tile == '|') {
                // moving left or right, so splitter is "|"
                addBeam(Beam(position, 0, 1))
                addBeam(Beam(position, 0, -1))
                running = false

            } else if (beam.deltaY != 0 && tile == '-') {
                // moving up or down so splitter is "-"
                addBeam(Beam(position, 1, 0))
                addBeam(Beam(position, -1, 0))
                running = false
            }

            if (running) {
                position = Coordinate(position.x + beam.deltaX, position.y + beam.deltaY)

                if (!(0..maxWidth).contains(position.x) ||
                    !(0..maxHeight).contains(position.y)
                ) {
                    running = false
                }
            }
        }
    }

    fun addBeam(beam: Beam) {
        if (!seenBeams.contains(beam.toString())) {
            // println("adding beam $beam")
            seenBeams.add(beam.toString())
            beamQueue.add(beam)
            energizedTiles.add(beam.startCoordinate)
        }
    }
}