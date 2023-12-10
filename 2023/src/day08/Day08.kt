package day08

import java.io.File

fun main() {
    val input = File("src/day08/day08.txt").readLines()
    val instructions = input[0]
    val map = NodeMap(input.drop(2))
    println(runInstructions(instructions, map))
    println(runInstructionsAsGhost(instructions, map))
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

    fun getStartingNodes(): List<Node> {
        return nodeMapping.entries.filter { it.key.endsWith("A") }.map { it.value }
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

fun runInstructionsAsGhost(instructions: String, nodeMap: NodeMap): Long {
    val instructionList = instructions.toList()
    // Find all the starting nodes
    val nodeList = nodeMap.getStartingNodes()
    var initialZ = MutableList(nodeList.size) { 0L }
    var stepCounts = MutableList(nodeList.size) { 0L }
    nodeList.forEachIndexed { index, startNode ->
        var node = startNode
        var stepCount = 0
        var foundZ = false
        while (!node.id.endsWith("Z") || !foundZ) {
            if (node.id.endsWith("Z")) {
                println("${startNode.id} : found Z at $stepCount")
                foundZ = true
                initialZ[index] = stepCount.toLong()
            }
            val instruction = instructionList[stepCount++ % instructionList.size]
            // println("$stepCount currentNode: ${node.id} ${node.left} ${node.right} $instruction")
            node = when (instruction) {
                'R' -> nodeMap.getNode(node.right)
                'L' -> nodeMap.getNode(node.left)
                else -> throw IllegalArgumentException("$instruction is not valid")
            }
        }

        stepCounts[index] = stepCount.toLong()
        println("${startNode.id} : found second Z at $stepCount : period = ${stepCount - initialZ[index]}")
    }

    // Now get the least common multiple : meh, google sheets

    return stepCounts.reduce { acc, i -> acc * i }
}

fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) {
        a
    } else {
        gcd(b, a % b)
    }
}