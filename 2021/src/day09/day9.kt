package day09

import java.io.File

fun main() {
    val heightMap = parseData(File("src/day09", "day9input.txt").readLines())
    println(getRiskFactor(heightMap.findLowPoints()))
}

fun parseData(input: List<String>): HeightMap {
    return HeightMap(input.map { it.toCharArray().map { digit -> digit.digitToInt() }.toIntArray() }.toTypedArray())
}

fun getRiskFactor(input: List<Int>): Int {
    return input.sumOf { it + 1 }
}

data class HeightMap(val heightData: Array<IntArray>) {


    fun findLowPoints(): List<Int> {
        return heightData.mapIndexed { rowIndex, ints ->
            val maxCol = ints.size - 1
            ints.filterIndexed { colIndex, height ->
                isLowPoint(rowIndex, colIndex, height, maxCol)
            }
        }.flatMap { it.toList() }
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