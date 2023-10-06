package day20

import org.junit.Test
import kotlin.test.assertEquals

internal class Day20KtTest {
    @Test
    fun testParse() {
        val simpleList = getNodes(listOf(1, 2, -3, 3, -2, 0, 4))
        assertEquals(7, simpleList.size)
        assertEquals(1, simpleList[0].value)
        assertEquals(2, simpleList[0].next?.value)
        assertEquals(1, simpleList[1].previous?.value)
        // Test the ring
        assertEquals(4, simpleList[0].previous?.value)
        assertEquals(1, simpleList.last().next?.value)
    }

    @Test
    fun testMoveNode() {
        val simpleList = getNodes(listOf(1, 2, -3, 3, -2, 0, 4))
        moveNode(simpleList[0], 1, false)
        assertEquals(2, simpleList[0].previous?.value)
        assertEquals(-3, simpleList[0].next?.value)
        assertEquals(1, simpleList[1].next?.value)
        assertEquals(1, simpleList[2].previous?.value)

        testOrder(listOf(2, 1, -3, 3, -2, 0, 4), simpleList[1])

        moveNode(simpleList[1], 2, false)

        testOrder(listOf(1, -3, 2, 3, -2, 0, 4), simpleList[0])

        moveNode(simpleList[2], 3, true)
        simpleList[0].printNext(7)
        testOrder(listOf(1, 2, 3, -2, -3, 0, 4), simpleList[0])
    }

    private fun testOrder(input: List<Int>, node: Node) {
        var testNode: Node? = node
        input.forEach {
            // println("$it, ${testNode?.value}")
            assertEquals(it, testNode?.value)

            testNode = testNode?.next
        }
    }

    @Test
    fun testMixNodes() {
        val simpleList = getNodes(listOf(1, 2, -3, 3, -2, 0, 4))
        mixNodes(simpleList)
        simpleList[0].printNext(7)
        testOrder(listOf(1, 2, -3, 4, 0, 3, -2), simpleList[0])
    }

    @Test
    fun testGroveNumbers() {
        val simpleList = getNodes(listOf(1, 2, -3, 3, -2, 0, 4))
        mixNodes(simpleList)
        val zeroNode = simpleList.find { it.value == 0 }
        assertEquals(4, zeroNode!!.getNextValue(1000)!!.value)
        assertEquals(-3, zeroNode!!.getNextValue(2000)!!.value)
        assertEquals(2, zeroNode!!.getNextValue(3000)!!.value)

        assertEquals(3, getGroveNumber(simpleList))
    }
}