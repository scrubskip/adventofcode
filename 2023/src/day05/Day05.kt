package day05

import java.io.File

fun main() {
    val input = File("src/day05/Day05.txt").readLines()
    val almanac = Almanac(input)

    println(almanac.getSeedValues().minOf { it })
    println(almanac.getMinLocationNumberForSeedRange())
}

class Almanac(input: List<String>) {
    private val mapSegments = mutableListOf<MapSegment>()
    private val seeds: List<Long>

    init {
        // Parse the input
        val iterator = input.listIterator()
        // First line is seeds:
        seeds = parseSeeds(iterator.next())

        while (iterator.hasNext()) {
            val currentString = iterator.next()
            if (currentString.contains("map:")) {
                println("creating $currentString")
                // Create a new map segment
                mapSegments.add(MapSegment())
            } else if (currentString.isNotEmpty()) {
                mapSegments.last().addRangeString(currentString)
            }
        }
    }

    private fun parseSeeds(input: String): List<Long> {
        return input.substring(input.indexOf(":") + 2).split(" ").map { it.toLong() }
    }

    fun getMappedValue(input: Long): Long {
        var currentValue = input
        mapSegments.forEachIndexed { index, mapSegment ->
            // println("$index: $currentValue ${mapSegment.getMappedValue(currentValue)}")
            currentValue = mapSegment.getMappedValue(currentValue)
        }
        return currentValue
    }

    fun getSeedValues(): List<Long> {
        return seeds.map { getMappedValue(it) }
    }

    /**
     * Process the seed values as if they are ranges, not individual
     */
    fun getMinLocationNumberForSeedRange(): Long {
        var currentSeed: Long? = null


        seeds.chunked(2).forEach {
            println("Processing ${it[0]}")
            for (seed in it[0]..it[0] + it[1]) {
                val seedValue = getMappedValue(seed)
                if (currentSeed == null || seedValue < currentSeed!!) {
                    currentSeed = seedValue
                }
            }
        }
        return currentSeed!!
    }
}

class MapSegment {
    private val rangeMappers: MutableList<RangeMapper> = mutableListOf()

    fun addRangeString(input: String) {
        val rangeParts = input.split(" ", limit = 3).map { it.toLong() }
        rangeMappers.add(RangeMapper(rangeParts[0], rangeParts[1], rangeParts[2]))
    }

    fun getMappedValue(input: Long): Long {
        rangeMappers.forEach {
            if (it.isInRange(input)) {
                return it.getMappedValue(input)
            }
        }
        return input
    }
}

class RangeMapper(destinationRangeStart: Long, sourceRangeStart: Long, rangeLength: Long) {
    private val destinationStart = destinationRangeStart
    private val range = rangeLength
    val sourceStart = sourceRangeStart


    fun isInRange(input: Long): Boolean {
        return input >= sourceStart && input <= sourceStart + range
    }

    fun getMappedValue(input: Long): Long {
        if (isInRange(input)) {
            return input - sourceStart + destinationStart
        }
        throw IllegalArgumentException("$input not in range $sourceStart to ${sourceStart + range}")
    }
}