package day20

import java.io.File

fun main() {
    val file = File("src/day20", "day20input.txt").readLines()
    val algorithm = file[0]
    val image = Image.parse(file.drop(2))
    println(algorithm)
    println(image.getBounds())
    println(image)

    val enhance = image.enhance(algorithm)
    //println(enhance)
    val enhance2 = enhance.enhance(algorithm)
    //println(enhance2)
    println(enhance2.getOnPixels())

    var imageEnhanced = image
    (1..50).forEach { _ ->
        imageEnhanced = imageEnhanced.enhance(algorithm)
    }
    println(imageEnhanced.getOnPixels())
}

typealias Point = Pair<Int, Int>

class Image {
    private val pixels: MutableMap<Point, Boolean> = mutableMapOf()
    private var lowestX: Int = 0
    private var lowestY: Int = 0
    private var greatestX: Int = 0
    private var greatestY: Int = 0

    var voidPixel = false

    private val MARGIN = 3

    fun getPixel(coordinate: Point): Boolean {
        if (coordinate.first < lowestX || coordinate.first > greatestX || coordinate.second < lowestY || coordinate.second > greatestY) {
            return voidPixel
        }
        return pixels.getOrDefault(coordinate, false)
    }

    fun setPixel(coordinate: Point, value: Boolean) {
        pixels[coordinate] = value
        if (value) {
            if (coordinate.first < lowestX) lowestX = coordinate.first
            if (coordinate.first > greatestX) greatestX = coordinate.first
            if (coordinate.second < lowestY) lowestY = coordinate.second
            if (coordinate.second > greatestY) greatestY = coordinate.second
        }
    }

    fun enhance(algorithm: String): Image {
        val newImage = Image()

        if (algorithm[0] == '#') newImage.voidPixel = !voidPixel

        for (x in lowestX - MARGIN..greatestX + MARGIN) {
            for (y in lowestY - MARGIN..greatestY + MARGIN) {
                // for each pixel in this image, get a window
                val coordinate = Point(x, y)
                val pixel = algorithm[getLookupIndex(coordinate)] == '#'
                newImage.setPixel(coordinate, pixel)
            }
        }

        return newImage
    }

    fun getBounds(): Pair<Point, Point> {
        return Pair(Point(lowestX, lowestY), Point(greatestX, greatestY))
    }

    fun getLookupIndex(coordinate: Point): Int {
        return buildList<Char> {
            for (y in coordinate.second - 1..coordinate.second + 1) {
                for (x in coordinate.first - 1..coordinate.first + 1) {
                    add(if (getPixel(Point(x, y))) '1' else '0')
                }
            }
        }.joinToString(separator = "").padStart(12, '0').toInt(2)
    }

    fun getOnPixels(): Int {
        return pixels.count { it.value }
    }

    override fun toString(): String {
        return (lowestY - MARGIN..greatestY + MARGIN).joinToString(separator = "\n") { y ->
            (lowestX - MARGIN..greatestX + MARGIN).map { x ->
                if (getPixel(Point(x, y)) || voidPixel) '#' else '.'
            }.joinToString("")
        }
    }

    companion object {
        fun parse(input: Iterable<String>): Image {
            val image = Image()

            input.withIndex().forEach { row ->
                row.value.withIndex().forEach { pixel ->
                    if (pixel.value == '#') {
                        image.setPixel(Point(pixel.index, row.index), true)
                    }
                }
            }

            return image
        }
    }
}

