package day18

import java.io.File
import kotlin.math.ceil

fun main() {
    val input = File("src/day18", "day18input.txt").readLines().map { Snailfish.parseFish(it) }
    val sum = input.reduce { acc: Snailfish, snailfish: Snailfish -> acc + snailfish }
    println(sum.getMagnitude())
    println(findGreatestMagnitudePair(input))
}

fun findGreatestMagnitudePair(input: List<Snailfish>): Long {
    var greatestMag: Long = 0
    for (fish in input) {
        for (otherFish in input) {
            if (fish != otherFish) {
                val mag = (fish + otherFish).getMagnitude()
                if (mag > greatestMag) {
                    // println("found max $fish $otherFish $mag")
                    greatestMag = mag
                }
            }
        }
    }
    return greatestMag
}

open class Snailfish() {
    var left: Snailfish? = null
    var right: Snailfish? = null
    var parent: Snailfish? = null

    override fun toString(): String {
        return "[$left,$right]"
    }

    operator fun plus(other: Snailfish): Snailfish {
        val newFish = Snailfish()
        newFish.left = this.copy()
        newFish.left?.parent = newFish
        newFish.right = other.copy()
        newFish.right?.parent = newFish

        newFish.reduce()

        return newFish
    }

    fun reduce() {
        var explode = getFirstDepthGreaterThan(4)
        var split = getFirstChildGreaterThanEqualTo(10)
        do {
            explode?.parent?.explodeChild(explode)
            explode = getFirstDepthGreaterThan(4)
            if (explode != null) {
                continue
            }
            split = getFirstChildGreaterThanEqualTo(10)
            if (split != null) split?.parent?.splitChild(split)
            explode = getFirstDepthGreaterThan(4)
        } while (explode != null || split != null)
    }

    private fun splitChild(split: SnailfishValue) {
        //println("splitting $split")
        val newFish = Snailfish()
        newFish.left = SnailfishValue(split.number / 2)
        newFish.left?.parent = newFish
        newFish.right = SnailfishValue(ceil(split.number.toDouble() / 2).toLong())
        newFish.right?.parent = newFish
        newFish.parent = this
        if (split == left) {
            left = newFish
        } else {
            right = newFish
        }
    }

    private fun explodeChild(snailfish: Snailfish) {
        //println("exploding $snailfish")
        val leftValue = (snailfish.left as SnailfishValue).number
        val rightValue = (snailfish.right as SnailfishValue).number

        val isLeft = snailfish == left

        // find leftmost value
        val firstLeftValue = snailfish.getFirstLeft()
        firstLeftValue?.let {
            it.number = it.number + leftValue
        }
        val firstRightValue = snailfish.getFirstRight()
        firstRightValue?.let {
            it.number = it.number + rightValue
        }
        val newValue = SnailfishValue(0)
        newValue.parent = this
        if (isLeft) {
            left = newValue
        } else {
            right = newValue
        }
    }

    fun getFirstLeft(): SnailfishValue? {
        var node = parent
        var currentChild = this
        while (node != null && node.left == currentChild) {
            currentChild = node
            node = node.parent
        }
        if (node != null) {
            return node.left?.getRightmostValue()
        }
        return null
    }

    fun getFirstRight(): SnailfishValue? {
        var node = parent
        var currentChild = this
        while (node != null && node.right == currentChild) {
            currentChild = node
            node = node.parent
        }
        if (node != null) {
            return node.right?.getLeftmostValue()
        }
        return null
    }

    fun getRightmostValue(): SnailfishValue? {
        if (this is SnailfishValue) {
            return this
        } else if (right != null) {
            return right?.getRightmostValue()
        }
        return null
    }

    fun getLeftmostValue(): SnailfishValue? {
        if (this is SnailfishValue) {
            return this
        } else if (left != null) {
            return left?.getLeftmostValue()
        }
        return null
    }

    fun getFirstDepthGreaterThan(maxDepth: Int, currentDepth: Int = 0): Snailfish? {
        if (left is SnailfishValue && right is SnailfishValue) {
            if (currentDepth >= maxDepth) {
                return this
            }
        }
        if (left != null) {
            val foundLeft = left?.getFirstDepthGreaterThan(maxDepth, currentDepth + 1)
            if (foundLeft != null) {
                return foundLeft
            }
        }
        if (right != null) {
            val foundRight = right?.getFirstDepthGreaterThan(maxDepth, currentDepth + 1)
            if (foundRight != null) {
                return foundRight
            }
        }
        return null
    }

    fun getFirstChildGreaterThanEqualTo(value: Int): SnailfishValue? {
        if (this is SnailfishValue && this.number >= value) {
            return this
        }
        if (left != null) {
            val foundLeft = left?.getFirstChildGreaterThanEqualTo(value)
            if (foundLeft != null) {
                return foundLeft
            }
        }
        if (right != null) {
            val foundRight = right?.getFirstChildGreaterThanEqualTo(value)
            if (foundRight != null) {
                return foundRight
            }
        }
        return null
    }

    open fun getMagnitude(): Long {
        return (left?.getMagnitude() ?: 0).times(3) + (right?.getMagnitude() ?: 0).times(2)
    }

    private fun copy(): Snailfish {
        return parseFish(toString())
    }

    companion object {
        fun parseFish(input: String): Snailfish {
            return parseFishInner(input, 0).first
        }

        private fun parseFishInner(input: String, startIndex: Int): Pair<Snailfish, Int> {
            var index = startIndex
            var currentFish = Snailfish()

            while (index < input.length) {
                if (input[index] == '[') {
                    with(parseFishInner(input, index + 1)) {
                        currentFish.left = first
                        first.parent = currentFish
                        index = second
                    }
                } else if (input[index] == ',') {
                    with(parseFishInner(input, index + 1)) {
                        currentFish.right = first
                        first.parent = currentFish
                        index = second
                    }
                } else if (input[index].isDigit()) {
                    // find next non digit
                    val nextIndex = input.indexOfAny(listOf(",", "]"), index)
                    require(nextIndex != -1)
                    val valueFish = SnailfishValue(input.substring(index, nextIndex).toLong())
                    return Pair(valueFish, nextIndex)
                } else if (input[index] == ']') {
                    return Pair(currentFish, index + 1)
                } else {
                    throw Exception("invalid character ${input[index]}")
                }
            }
            return Pair(currentFish, index)
        }
    }
}

class SnailfishValue(var number: Long) : Snailfish() {
    override fun toString(): String {
        return number.toString()
    }

    override fun getMagnitude(): Long {
        return number
    }
}