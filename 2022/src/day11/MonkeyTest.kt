package day11

import kotlin.test.Test
import kotlin.test.assertEquals

internal class MonkeyTest {
    @Test
    fun testSample() {
        val monkeyHolder = getMonkeyHolder()

        repeat(20) {
            monkeyHolder.runRound()
            monkeyHolder.printState()
        }

        assertEquals(10605, monkeyHolder.getMonkeyBusiness())
    }

    @Test
    fun testSample2() {
        val monkeyHolder = getMonkeyHolder()

        repeat(10000) {
            if (it == 20 || it == 100 || it % 1000 == 0) {
                println("Round $it : ${monkeyHolder.getInspectionCounts()}")
            }
            monkeyHolder.runRound(worryOn = false)
        }

        println(monkeyHolder.getInspectionCounts())

        assertEquals(2713310158, monkeyHolder.getMonkeyBusiness())
    }

    private fun getMonkeyHolder(): MonkeyHolder {
        val monkeyHolder = MonkeyHolder()

        monkeyHolder.addMonkey(
            Monkey(
                0,
                listOf(79, 98),
                { old -> old * 19 },
                23,
                2,
                3,
                monkeyHolder::passItem
            )
        )
        monkeyHolder.addMonkey(
            Monkey(
                1,
                listOf(54, 65, 75, 74),
                { old -> old + 6 },
                19,
                2,
                0,
                monkeyHolder::passItem
            )
        )
        monkeyHolder.addMonkey(
            Monkey(
                2,
                listOf(79, 60, 97),
                { old -> old * old },
                13,
                1,
                3,
                monkeyHolder::passItem
            )
        )
        monkeyHolder.addMonkey(
            Monkey(
                3,
                listOf(74),
                { old -> old + 3 },
                17,
                0,
                1,
                monkeyHolder::passItem
            )
        )
        return monkeyHolder
    }


}