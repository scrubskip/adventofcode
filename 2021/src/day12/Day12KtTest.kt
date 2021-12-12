package day12

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test
import kotlin.test.assertContentEquals

internal class Day12KtTest {
    @Test
    fun testExample() {
        val input = listOf(
            "start-A",
            "start-b",
            "A-c",
            "A-b",
            "b-d",
            "A-end",
            "b-end"
        )

        val mapper = Mapper()

        for (mapping in input.map { it.split("-") }) {
            mapper.addMapping(mapping[0], mapping[1])
        }

        //assertContentEquals(listOf("A", "b"), mapper.getConnections("start"))
        // assertContentEquals(listOf("c", "b", "end", "start"), mapper.getChildren("A"))


        val paths = mapper.getPaths()
        assertEquals(10, paths.size)

        val morePaths = mapper.getPaths(oneDupeAllowed = true)
        println(morePaths.joinToString(separator = "\n"))
        assertEquals(36, morePaths.size)
    }

    @Test
    fun testBiggerExample() {
        val input = listOf(
            "dc-end",
            "HN-start",
            "start-kj",
            "dc-start",
            "dc-HN",
            "LN-dc",
            "HN-end",
            "kj-sa",
            "kj-HN",
            "kj-dc"
        )

        val mapper = Mapper()

        for (mapping in input.map { it.split("-") }) {
            mapper.addMapping(mapping[0], mapping[1])
        }
        val paths = mapper.getPaths()
        assertEquals(19, paths.size)

        val morePaths = mapper.getPaths(oneDupeAllowed = true).sortedBy { it.size }
        println(morePaths.joinToString(separator = "\n"))
        assertEquals(103, morePaths.size)
    }

    @Test
    fun testBiggestExample() {
        val input = listOf(
            "fs-end",
            "he-DX",
            "fs-he",
            "start-DX",
            "pj-DX",
            "end-zg",
            "zg-sl",
            "zg-pj",
            "pj-he",
            "RW-he",
            "fs-DX",
            "pj-RW",
            "zg-RW",
            "start-pj",
            "he-WI",
            "zg-he",
            "pj-fs",
            "start-RW"
        )

        val mapper = Mapper()

        for (mapping in input.map { it.split("-") }) {
            mapper.addMapping(mapping[0], mapping[1])
        }
        val paths = mapper.getPaths()
        assertEquals(226, paths.size)

        val morePaths = mapper.getPaths(oneDupeAllowed = true)
        assertEquals(3509, morePaths.size)
    }
}