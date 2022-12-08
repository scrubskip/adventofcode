package day07

import java.io.File

fun main() {
    val input = File("src/day07/day07input.txt").readLines()
    val parser = FileSystemParser(input)
    parser.processCommands()
    val node = parser.getRootNode()
    println(getSumDirectoriesAtMost(node as FolderNode, 100000))
}

class FileSystemParser {
    private var _currentNode: FolderNode? = null
    private var _rootNode: FolderNode? = null
    private val _commands: List<String>
    var index: Int

    constructor(commands: List<String>) {
        this._commands = commands
        index = 0
    }

    fun getRootNode(): Node? {
        return _rootNode
    }

    fun processCommands() {
        var processingFiles: Boolean = false
        while (index < this._commands.size) {
            val command = _commands[index]
            if (command.startsWith("$ cd")) {
                processingFiles = false
                // create child or root with name
                changeDirectory(command.substring(5))
            } else if (command.startsWith("$ ls")) {
                // Now process the list of files
                processingFiles = true
            } else {
                if (processingFiles) {
                    if (command.startsWith("dir")) {
                        // create a new directory in the current node
                        _currentNode!!.addChild(FolderNode(command.substring(4), _currentNode))
                    } else {
                        // This is a file
                        val (size, name) = command.split(" ")
                        _currentNode!!.addChild(FileNode(name, size.toInt()))
                    }
                } else {
                    throw Exception("Processing file before ls called")
                }
            }
            index++
        }
    }

    private fun changeDirectory(name: String) {
        if (name == "/") {
            // Create the root node
            if (_rootNode == null) {
                _rootNode = FolderNode("/", null)
            }
            _currentNode = _rootNode
        } else if (name == "..") {
            if (_currentNode == null) {
                throw Exception("Can't go up from null directory")
            }
            _currentNode = _currentNode!!.getParent()
        } else {
            if (_currentNode != null) {
                var node = _currentNode!!.getChildFolder(name)
                if (node == null) {
                    // create the node
                    node = FolderNode(name, _currentNode)
                    _currentNode!!.addChild(node)
                }
                _currentNode = node
            } else {
                throw Exception("Can't create a non root node when there is no current node. $name")
            }
        }
    }

}

fun getSumDirectoriesAtMost(node: FolderNode, limit: Int): Int {
    val nodes = mutableListOf<FolderNode>(node)
    val foundNodes = mutableListOf<FolderNode>()
    while (nodes.isNotEmpty()) {
        val currentNode = nodes.removeFirst()
        if (currentNode.getSize() <= limit) {
            foundNodes.add(currentNode)
        }
        nodes.addAll(currentNode.getFolderChildren())
    }
    return foundNodes.sumOf { it.getSize() }
}

abstract class Node {
    private val _name: String

    constructor(name: String) {
        this._name = name
    }

    fun getName(): String {
        return _name
    }

    abstract fun getSize(): Int

    abstract fun toString(indent: Int = 0): String
}

class FolderNode : Node {
    private val _parent: FolderNode?
    private val _children: MutableList<Node>

    constructor(name: String, parent: FolderNode?) : super(name) {
        this._parent = parent
        this._children = mutableListOf()
    }

    fun getParent(): FolderNode? {
        return _parent
    }

    fun getFolderChildren(): List<FolderNode> {
        return _children.filterIsInstance<FolderNode>()
    }

    override fun getSize(): Int {
        return _children.sumOf { it.getSize() }
    }

    override fun toString(indent: Int): String {
        val childString = _children.joinToString("\n") { it.toString(indent + 2) }
        return ' '.toString().repeat(indent) + "- ${getName()} (dir)\n$childString"
    }

    fun getChildFolder(name: String): FolderNode? {
        var node = this._children.find { it.getName() == name }
        return if (node is FolderNode) {
            node
        } else {
            null
        }
    }

    fun addChild(node: Node) {
        _children.add(node)
    }
}

class FileNode : Node {
    private val _size: Int

    constructor(name: String, size: Int) : super(name) {
        this._size = size
    }

    override fun getSize(): Int {
        return _size
    }

    override fun toString(indent: Int): String {
        return ' '.toString().repeat(indent) + "- ${getName()} (file, size=$_size)"
    }
}