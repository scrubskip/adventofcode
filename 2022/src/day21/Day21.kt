package day21

import java.io.File

fun main() {
    val monkeys = Monkey.parse(File("src/day21/day21input.txt").readLines())
    val rootMonkey = monkeys["root"]
    println("${monkeys.size}")
    println(rootMonkey!!.getValue(monkeys))
}

abstract class Monkey(val key: String) {
    abstract fun getValue(monkeys: Map<String, Monkey>): Long

    companion object {
        fun parse(input: String): Monkey {
            // First 4 characters are the key
            val key = input.substring(0, 4)
            val value = input.substring(6)
            val number = value.toLongOrNull()
            return if (number != null) {
                NumberMonkey(key, number)
            } else {
                val key1 = input.substring(6, 6 + 4)
                val operation = input.substring(11, 11 + 1)
                val key2 = input.substring(13, 13 + 4)
                DependentMonkey(key, key1, key2, operation)
            }
        }

        fun parse(input: List<String>): Map<String, Monkey> {
            return input.map { parse(it) }.associateBy { monkey -> monkey.key }
        }
    }
}

class NumberMonkey(key: String, private val value: Long) : Monkey(key) {

    override fun getValue(monkeys: Map<String, Monkey>): Long {
        return value
    }
}

class DependentMonkey(
    key: String,
    val monkeyKey1: String,
    val monkeyKey2: String,
    val operation: String
) : Monkey(key) {
    var cachedValue: Long? = null

    override fun getValue(monkeys: Map<String, Monkey>): Long {
        if (cachedValue != null) {
            return cachedValue!!
        }

        val monkeyVal1 = monkeys[monkeyKey1]!!.getValue(monkeys)
        val monkeyVal2 = monkeys[monkeyKey2]!!.getValue(monkeys)
        cachedValue = when (operation) {
            "+" -> monkeyVal1 + monkeyVal2
            "-" -> monkeyVal1 - monkeyVal2
            "*" -> monkeyVal1 * monkeyVal2
            "/" -> monkeyVal1 / monkeyVal2
            else -> throw Exception("Invalid operation $operation")
        }
        return cachedValue!!
    }
}