package day20

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val input = getNodes(File("src/day20/day20input.txt").readLines().map {
        it.toInt()
    })
    input[0].printNext(10)
    println("${input.size}")
    mixNodes(input)
    input[0].printNext(10)


    val startNode = input.find { it.value == 0 }
    val grove1 = startNode?.getNextValue(1000)
    val grove2 = startNode?.getNextValue(2000)
    val grove3 = startNode?.getNextValue(3000)
    println("grove1 : ${grove1!!.value}")
    println("grove2 : ${grove2!!.value}")
    println("grove3 : ${grove3!!.value}")
    println(getGroveNumber(input))
}

fun getNodes(values: List<Int>): List<Node> {
    val returnList: MutableList<Node> = mutableListOf()
    if (values.isEmpty()) {
        return returnList
    }

    var current = Node(values.first())
    returnList.add(current)
    values.drop(1).forEach {
        val newNode = Node(it)
        current.setChild(newNode)
        current = newNode
        returnList.add(newNode)
    }
    // Now patch up the start and end so it's a ring
    returnList.first().previous = returnList.last()
    returnList.last().next = returnList.first()
    return returnList
}

fun mixNodes(values: List<Node>) {
    values.forEach {
        moveNode(it, it.value.absoluteValue, it.value < 0)
    }
}

fun moveNode(node: Node, count: Int, backward: Boolean) {
    if (count == 0) {
        return
    }
    // Find new parent
    var newParent: Node? = if (backward) node.previous else node
    repeat(count) {
        newParent = if (backward) newParent?.previous else newParent?.next
    }
    // Now snip
    node.previous?.setChild(node.next)
    var newChild = newParent?.next
    newParent?.setChild(node)
    node.setChild(newChild)
}

fun getGroveNumber(nodes: List<Node>): Int {
    val startNode = nodes.find { it.value == 0 }
    val grove1 = startNode?.getNextValue(1000)
    val grove2 = grove1?.getNextValue(1000)
    val grove3 = grove2?.getNextValue(1000)

    return grove1!!.value + grove2!!.value + grove3!!.value
}


class Node(val value: Int) {
    var previous: Node? = null
    var next: Node? = null

    fun setChild(node: Node?) {
        node?.previous = this
        next = node
    }

    fun getNextValue(iteration: Int): Node? {
        var current: Node? = this
        repeat(iteration) {
            current = current?.next
        }
        return current
    }

    fun printNext(count: Int) {
        val list: MutableList<Int> = mutableListOf()
        var loopNode: Node? = this
        repeat(count) {
            loopNode?.let {
                list.add(it.value)
            }
            loopNode = loopNode?.next
        }
        println("$list")
    }
}