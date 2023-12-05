package day05

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File

fun main() {
    val input = File("src/day05/Day05.txt").readLines()
    val almanac = Almanac(input)

    println(almanac.getSeedValues().minOf { it })
    runBlocking {
        println(almanac.getMinLocationNumberForSeedRange())
    }
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
    suspend fun getMinLocationNumberForSeedRange(): Long {
        var currentSeed: Long? = null

        println("Starting chunks")

        currentSeed = coroutineScope {
            val deferred = mutableListOf<Deferred<Long>>()
            seeds.chunked(2).forEach {
                deferred.add(async {
                    return@async getLowestSeedValue(it[0], it[1])
                })
            }
            println("Waiting chunks")
            return@coroutineScope deferred.awaitAll().min()
        }

        return currentSeed!!
    }

    private suspend fun getLowestSeedValue(start: Long, end: Long): Long {
        var currentSeed: Long? = null
        withContext(Dispatchers.Default) {
            println("Processing $start")
            for (seed in start..start + end) {
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