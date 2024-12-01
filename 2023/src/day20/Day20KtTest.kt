package day20

import kotlin.test.Test
import kotlin.test.assertEquals

class Day20KtTest {
    @Test
    fun testParse() {
        val input = listOf(
            "broadcaster -> a, b, c",
            "%a -> b",
            "%b -> c",
            "%c -> inv",
            "&inv -> a",
        )
        val pulseSender = PulseSender(input)
        assertEquals(5, pulseSender.modules.size)
        val broadcaster = pulseSender.modules["broadcaster"]
        assertEquals(3, broadcaster!!.destinationModules.size)

        pulseSender.pressButton()
        assertEquals(8, pulseSender.lowCount)
        assertEquals(4, pulseSender.highCount)

        val pulseSenderCount = PulseSender(input)
        pulseSenderCount.printSend = false
        repeat(1000) { pulseSenderCount.pressButton() }
        assertEquals(32000000, pulseSenderCount.lowCount * pulseSenderCount.highCount)
    }

    @Test
    fun testExample() {
        val input = listOf(
            "broadcaster -> a",
            "%a -> inv, con",
            "&inv -> b",
            "%b -> con",
            "&con -> output",
        )
        val pulseSender = PulseSender(input)
        pulseSender.pressButton()
        pulseSender.pressButton()
        pulseSender.pressButton()
        pulseSender.pressButton()

        val pulseSenderCount = PulseSender(input)
        pulseSenderCount.printSend = false
        repeat(1000) { pulseSenderCount.pressButton() }
        assertEquals(11687500, pulseSenderCount.lowCount * pulseSenderCount.highCount)
    }

    @Test
    fun testUntilRx() {
        val input = listOf(
            "broadcaster -> a",
            "%a -> inv, con",
            "&inv -> b",
            "%b -> con",
            "con -> rx",
        )

        val pulseSender = PulseSender(input)
        println(pulseSender.pressButtonUntilRx())
    }
}