package day12

import java.io.File

fun main() {
    val mapper = Mapper()
    for (mapping in File("src/day12", "day12input.txt").readLines().map { it.split("-") }) {
        mapper.addMapping(mapping[0], mapping[1])
    }
    val paths = mapper.getPaths(oneDupeAllowed = true)
    println("${paths.size}")
}

class Mapper() {

    // Maps a node to its children nodes
    val pathMap: MutableMap<String, MutableList<String>> = mutableMapOf()

    fun addMapping(parentId: String, childId: String) {
        pathMap.putIfAbsent(parentId, mutableListOf())
        pathMap[parentId]!!.add(childId)

        pathMap.putIfAbsent(childId, mutableListOf())
        pathMap[childId]!!.add(parentId)
    }

    fun getConnections(parentId: String): List<String> {
        return pathMap.getOrDefault(parentId, listOf())
    }

    fun getPaths(oneDupeAllowed: Boolean = false): List<List<String>> {
        // Depth first search with ability to pop if going to a big node
        val paths = mutableListOf<List<String>>()

        paths.addAll(getAllPaths("start", mutableListOf(), oneDupeAllowed))

        return paths.distinctBy { it.joinToString() }
    }

    fun getAllPaths(id: String, currentPath: List<String>, oneDupeAllowed: Boolean = false): List<List<String>> {
        // For a given id current, iterate through the children
        val returnList = mutableListOf<List<String>>()

        for (connection in getConnections(id)) {
            if (isStart(connection)) {
                continue
            }
            if (isEnd(connection)) {
                returnList.add(currentPath + id + connection)
            } else {
                if (isBigNode(connection)) {
                    returnList.addAll(getAllPaths(connection, currentPath + id, oneDupeAllowed))
                } else {
                    var canAdd = !currentPath.contains(connection)
                    var oneSave = false
                    if (!canAdd && oneDupeAllowed) {
                        // trying to add the first time
                        oneSave = true
                        canAdd = true
                    }
                    if (canAdd) {
                        // only add if the child wasn't in the list before
                        returnList.addAll(getAllPaths(connection, currentPath + id, oneDupeAllowed && !oneSave))
                    }
                }
            }
        }

        return returnList
    }

}

fun isBigNode(id: String): Boolean {
    return id == id.uppercase()
}

fun isStart(id: String): Boolean {
    return "start" == id
}

fun isEnd(id: String): Boolean {
    return "end" == id
}