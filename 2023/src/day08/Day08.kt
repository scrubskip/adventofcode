package day08

import java.io.File

fun main() {
    val input = File("src/day08/day08.txt").readLines()
    val instructions = input[0]
    val map = NodeMap(input.drop(2))
    println(runInstructions(instructions, map))
}

class Node(val id: String, val left: String, val right: String) {
}

class NodeMap(input: List<String>) {

    private val LINE = Regex("""(\w{3}) = \((\w{3}), (\w{3})\)""")

    private val nodeMapping: Map<String, Node>

    init {
        nodeMapping = input.map {
            val match = LINE.find(it)!!
            val (id, left, right) = match.destructured
            Node(id, left, right)
        }.associateBy { it.id }
    }

    fun getNode(id: String): Node {
        return nodeMapping[id]!!
    }
}

fun runInstructions(instructions: String, nodeMap: NodeMap): Int {
    val instructionList = instructions.toList()
    var node = nodeMap.getNode("AAA")
    var stepCount = 0
    while (node.id != "ZZZ") {
        val instruction = instructionList[stepCount++ % instructionList.size]
        // println("$stepCount currentNode: ${node.id} ${node.left} ${node.right} $instruction")
        node = when (instruction) {
            'R' -> nodeMap.getNode(node.right)
            'L' -> nodeMap.getNode(node.left)
            else -> throw IllegalArgumentException("$instruction is not valid")
        }
    }
    return stepCount
}