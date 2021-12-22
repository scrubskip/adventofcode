package day18

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class SnailfishParserTest {
    @Test
    fun testExample() {
        with(Snailfish.parseFish("[1,2]")) {
            assertEquals("1", left.toString())
            assertEquals("2", right.toString())
        }

        with(Snailfish.parseFish("[[1,2],3]")) {
            assertEquals("[1,2]", left.toString())
            assertEquals("1", left?.left.toString())
            assertTrue(left?.left?.parent == left)
            assertTrue(left?.parent == this)
            assertEquals("3", right.toString())
        }

        with(Snailfish.parseFish("[9,[8,7]]")) {
            assertEquals("9", left.toString())
            assertEquals("[8,7]", right.toString())
        }

        with(Snailfish.parseFish("[[1,9],[8,5]]")) {
            assertEquals("[1,9]", left.toString())
            assertEquals("[8,5]", right.toString())
        }
    }

    @Test
    fun testOperation() {
        val snail1 = Snailfish.parseFish("[1,2]")
        val snail2 = Snailfish.parseFish("[[3,4],5]")
        with((snail1 + snail2)) {
            assertEquals("[[1,2],[[3,4],5]]", toString())
        }
    }

    @Test
    fun testExplode() {
        with(Snailfish.parseFish("[[[[[9,8],1],2],3],4]")) {
            reduce()
            assertEquals("[[[[0,9],2],3],4]", toString())
        }

        with(Snailfish.parseFish("[7,[6,[5,[4,[3,2]]]]]")) {
            reduce()
            assertEquals("[7,[6,[5,[7,0]]]]", toString())
        }

        with(Snailfish.parseFish("[[6,[5,[4,[3,2]]]],1]")) {
            reduce()
            assertEquals("[[6,[5,[7,0]]],3]", toString())
        }

        with(Snailfish.parseFish("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")) {
            reduce()
            assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", toString())
        }
    }

    @Test
    fun testMultiple() {
        with(Snailfish.parseFish("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]")) {
            reduce()
            assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", toString())
        }
    }

    @Test
    fun testSum() {
        var input = listOf(
            "[1,1]", "[2,2]", "[3,3]", "[4,4]"
        ).map { Snailfish.parseFish(it) }
        with(input.reduce { acc: Snailfish, snailfish: Snailfish -> acc + snailfish }) {
            assertEquals("[[[[1,1],[2,2]],[3,3]],[4,4]]", toString())
        }

        input = listOf(
            "[1,1]", "[2,2]", "[3,3]", "[4,4]", "[5,5]"
        ).map { Snailfish.parseFish(it) }
        with(input.reduce { acc: Snailfish, snailfish: Snailfish -> acc + snailfish }) {
            assertEquals("[[[[3,0],[5,3]],[4,4]],[5,5]]", toString())
        }
    }

    @Test
    fun testMagnitude() {
        with(Snailfish.parseFish("[[9,1],[1,9]]")) {
            assertEquals(129, getMagnitude())
        }

        with(Snailfish.parseFish("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")) {
            assertEquals(1384, getMagnitude())
        }
    }

    @Test
    fun testSumExample() {
        val input = listOf(
            "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
            "[[[5,[2,8]],4],[5,[[9,9],0]]]",
            "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
            "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
            "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
            "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
            "[[[[5,4],[7,7]],8],[[8,3],8]]",
            "[[9,3],[[9,9],[6,[4,9]]]]",
            "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
            "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"
        ).map { Snailfish.parseFish(it) }
        val sum = input.reduce { acc: Snailfish, snailfish: Snailfish -> acc + snailfish }
        assertEquals(4140, sum.getMagnitude())
    }
}


