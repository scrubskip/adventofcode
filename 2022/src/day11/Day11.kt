package day11

fun main() {
    val monkeyHolder = MonkeyHolder()

    monkeyHolder.addMonkey(
        Monkey(
            0,
            listOf(76, 88, 96, 97, 58, 61, 67),
            { old -> old * 19 },
            3,
            2,
            3,
            monkeyHolder::passItem
        )
    )
    monkeyHolder.addMonkey(
        Monkey(
            1,
            listOf(93, 71, 79, 83, 69, 70, 94, 98),
            { old -> old + 8 },
            11,
            5,
            6,
            monkeyHolder::passItem
        )
    )
    monkeyHolder.addMonkey(
        Monkey(
            2,
            listOf(50, 74, 67, 92, 61, 76),
            { old -> old * 13 },
            19,
            3,
            1,
            monkeyHolder::passItem
        )
    )
    monkeyHolder.addMonkey(
        Monkey(
            3,
            listOf(76, 92),
            { old -> old + 6 },
            5,
            1,
            6,
            monkeyHolder::passItem
        )
    )
    monkeyHolder.addMonkey(
        Monkey(
            4,
            listOf(74, 94, 55, 87, 62),
            { old -> old + 5 },
            2,
            2,
            0,
            monkeyHolder::passItem
        )
    )
    monkeyHolder.addMonkey(
        Monkey(
            5,
            listOf(59, 62, 53, 62),
            { old -> old * old },
            7,
            4,
            7,
            monkeyHolder::passItem
        )
    )
    monkeyHolder.addMonkey(
        Monkey(
            6,
            listOf(62),
            { old -> old + 2 },
            17,
            5,
            7,
            monkeyHolder::passItem
        )
    )
    monkeyHolder.addMonkey(
        Monkey(
            7,
            listOf(85, 54, 53),
            { old -> old + 3 },
            13,
            4,
            0,
            monkeyHolder::passItem
        )
    )

    repeat(20) {
        monkeyHolder.runRound()
    }

    println(monkeyHolder.getMonkeyBusiness())
}

class MonkeyHolder {
    private var _monkeys: MutableMap<Int, Monkey> = mutableMapOf()

    fun addMonkey(monkey: Monkey) {
        _monkeys[monkey.id] = monkey
    }

    fun passItem(id: Int, item: Int) {
        // println("passing $item to $id")
        _monkeys[id]?.addItem(item)
    }

    fun runRound() {
        _monkeys.keys.toList().sorted().forEach {
            _monkeys[it]?.inspectItems()
        }
    }

    fun getInspectionCounts(): List<Int> {
        return _monkeys.values.map { it.getInspectionCount() }
    }

    fun getMonkeyBusiness(): Int {
        val counts = getInspectionCounts().sortedDescending()
        return counts[0] * counts[1]
    }

    fun printState() {
        _monkeys.keys.toList().sorted().forEach {
            println("Monkey $it : items : ${_monkeys[it]?.getItems()}")
        }
    }
}

class Monkey(
    // ID of this monkey
    val id: Int,
    items: List<Int>,
    // Operation to perform on each item when inspecting
    private val operation: (Int) -> Int,
    // Test denominator that monkey uses to determine true or false
    private val testDenominator: Int,
    // Which monkey to pass to if true
    private val trueTarget: Int,
    private val falseTarget: Int,
    private val itemPasser: (Int, Int) -> Unit
) {

    // Array of the items currently held. Value is the worry level.
    private val _heldItems = items.toMutableList()

    private var _inspectionCount = 0

    fun inspectItems() {
        while (_heldItems.isNotEmpty()) {
            _inspectionCount++
            var item = operation(_heldItems.removeFirst())
            item /= 3
            itemPasser(if (item % testDenominator == 0) trueTarget else falseTarget, item)
        }
    }

    fun addItem(item: Int) {
        _heldItems.add(item)
    }

    fun getItems(): List<Int> {
        return _heldItems.toList()
    }

    fun getInspectionCount(): Int {
        return _inspectionCount
    }
}