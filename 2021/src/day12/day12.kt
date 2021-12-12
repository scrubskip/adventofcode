package day12

import java.io.File

fun main() {
    val mapper = Mapper()
    for (mapping in File("src/day12", "day12input.txt").readLines().map { it.split("-") }) {
        mapper.addMapping(mapping[0], mapping[1])
    }
    val paths = mapper.getPaths()
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

    fun getPaths(): List<List<String>> {
        // Depth first search with ability to pop if going to a big node
        val paths = mutableListOf<List<String>>()

        paths.addAll(getAllPaths("start", mutableListOf()))

        return paths
    }

    fun getAllPaths(id: String, currentPath: List<String>): List<List<String>> {
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
                    returnList.addAll(getAllPaths(connection, currentPath + id))
                } else if (!currentPath.contains(connection)) {
                    // only add if the child wasn't in the list before
                    returnList.addAll(getAllPaths(connection, currentPath + id))
                }
            }
        }

        // If this id is also a big node, add itself to the children list too
        if (isBigNode(id)) {
            for (child in getConnections(id)) {
                if (!isEnd(child) && !isStart(child)) {
                    if (isBigNode(child) || !currentPath.contains(child)) {
                        // returnList.addAll(getAllPaths(id, currentPath + id + child))
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