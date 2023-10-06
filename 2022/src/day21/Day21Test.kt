package day21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import kotlin.test.Test

internal class Day21Test {
    @Test
    fun testParse() {
        val rootMonkey: DependentMonkey = Monkey.parse("root: pppw + sjmn") as DependentMonkey
        assertEquals("root", rootMonkey.key)
        assertEquals("pppw", rootMonkey.monkeyKey1)
        assertEquals("sjmn", rootMonkey.monkeyKey2)
        assertEquals("+", rootMonkey.operation)

        val numberMonkey: NumberMonkey = Monkey.parse("dbpl: 5") as NumberMonkey
        assertEquals("dbpl", numberMonkey.key)
        assertEquals(5, numberMonkey.getValue(mapOf()))
    }

    @Test
    fun testSimpleInput() {
        val monkeyStrings = listOf(
            "root: pppw + sjmn",
            "dbpl: 5",
            "cczh: sllz + lgvd",
            "zczc: 2",
            "ptdq: humn - dvpt",
            "dvpt: 3",
            "lfqf: 4",
            "humn: 5",
            "ljgn: 2",
            "sjmn: drzm * dbpl",
            "sllz: 4",
            "pppw: cczh / lfqf",
            "lgvd: ljgn * ptdq",
            "drzm: hmdt - zczc",
            "hmdt: 32"
        )
        val monkeys = Monkey.parse(monkeyStrings)
        assertEquals(15, monkeys.size)
        val rootMonkey = monkeys["root"]
        assertNotNull(rootMonkey)
        assertEquals(152, rootMonkey!!.getValue(monkeys))

        val testMonkey: DependentMonkey = monkeys["pppw"] as DependentMonkey
        assertEquals("cczh", testMonkey.monkeyKey1)
        assertEquals("lfqf", testMonkey.monkeyKey2)
        assertEquals("/", testMonkey.operation)

        val testMonkey2: NumberMonkey = monkeys["hmdt"] as NumberMonkey
        assertEquals(32, testMonkey2.getValue(monkeys))
    }
}