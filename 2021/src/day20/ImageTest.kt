package day20

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class ImageTest {
    val ALGORITHM = "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##" +
            "#..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###" +
            ".######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#." +
            ".#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#....." +
            ".#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.." +
            "...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#....." +
            "..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#"

    val IMAGE_STRING = listOf(
        "#..#.",
        "#....",
        "##..#",
        "..#..",
        "..###"
    )

    @Test
    fun testExample() {
        val image = Image.parse(IMAGE_STRING)
        println(image)
        assertEquals(512, ALGORITHM.length)
        assertEquals(34, image.getLookupIndex(Point(2, 2)))

        val enhance = image.enhance(ALGORITHM)
        println(enhance)
        val enhance2 = enhance.enhance(ALGORITHM)
        println(enhance2)
        assertEquals(35, enhance2.getOnPixels())

        var imageEnhanced = image
        (1..50).forEach { _ ->
            imageEnhanced = imageEnhanced.enhance(ALGORITHM)
        }
        assertEquals(3351, imageEnhanced.getOnPixels())
    }
}