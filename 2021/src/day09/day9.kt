package day09

import java.io.File

fun main() {
    val heightMap = parseData(File("src/day09", "day9input.txt").readLines())
    println(getRiskFactor(heightMap.findLowPointHeights()))

    val lowPoints = heightMap.findLowPoints()
    val largest3 = lowPoints.map { heightMap.findBasinSize(it) }.sortedDescending().take(3)
        .fold(1) { acc, value -> acc * value }
    println(largest3)
}

fun parseData(input: List<String>): HeightMap {
    return HeightMap(input.map { it.toCharArray().map { digit -> digit.digitToInt() }.toIntArray() }.toTypedArray())
}

fun getRiskFactor(input: List<Int>): Int {
    return input.sumOf { it + 1 }
}

data class HeightMap(val heightData: Array<IntArray>) {

    fun findLowPointHeights(): List<Int> {
        return heightData.mapIndexed { rowIndex, ints ->
            val maxCol = ints.size - 1
            ints.filterIndexed { colIndex, height ->
                isLowPoint(rowIndex, colIndex, height, maxCol)
            }
        }.flatMap { it.toList() }
    }

    fun findLowPoints(): List<Pair<Int, Int>> {
        val lowPointCoords = mutableListOf<Pair<Int, Int>>()
        heightData.forEachIndexed { rowIndex, ints ->
            val maxCol = ints.size - 1
            ints.forEachIndexed { colIndex, height ->
                if (isLowPoint(rowIndex, colIndex, height, maxCol)) {
                    lowPointCoords.add(Pair(rowIndex, colIndex))
                }
            }
        }
        return lowPointCoords
    }

    fun get(coordinate: Pair<Int, Int>): Int {
        return heightData[coordinate.first][coordinate.second]
    }

    fun findBasinSize(start: Pair<Int, Int>): Int {
        // flood fill looking for 9s
        val seenLocations = mutableSetOf<Pair<Int, Int>>()

        var candidateLocations = mutableListOf<Pair<Int, Int>>()
        candidateLocations.add(start)
        while (candidateLocations.size > 0) {
            val candidate = candidateLocations.removeFirst()

            if (get(candidate) == 9 || seenLocations.contains(candidate)) {
                continue
            }

            seenLocations.add(candidate)
            // add neighbors
            val rowIndex = candidate.first
            val colIndex = candidate.second

            if (rowIndex > 0) candidateLocations.add(Pair(rowIndex - 1, colIndex))
            if (colIndex > 0) candidateLocations.add(Pair(rowIndex, colIndex - 1))
            if (rowIndex < heightData.size - 1) candidateLocations.add(Pair(rowIndex + 1, colIndex))
            if (colIndex < heightData[rowIndex].size - 1) candidateLocations.add(Pair(rowIndex, colIndex + 1))
        }

        return seenLocations.size
    }

    fun isLowPoint(rowIndex: Int, colIndex: Int, height: Int, maxCol: Int): Boolean {
        val neighbors = buildList {
            if (rowIndex > 0) add(heightData[rowIndex - 1][colIndex])
            if (colIndex > 0) add(heightData[rowIndex][colIndex - 1])
            if (rowIndex < heightData.size - 1) add(heightData[rowIndex + 1][colIndex])
            if (colIndex < maxCol) add(heightData[rowIndex][colIndex + 1])
        }
        val returnValue = neighbors.all { height < it }
        if (returnValue) {
            println("Found low point $rowIndex $colIndex $height")
        }
        return returnValue
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HeightMap

        if (!heightData.contentDeepEquals(other.heightData)) return false

        return true
    }

    override fun hashCode(): Int {
        return heightData.contentDeepHashCode()
    }
}