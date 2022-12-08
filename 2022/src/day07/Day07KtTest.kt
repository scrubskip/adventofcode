package day07

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class Day07KtTest {

    @Test
    fun testParse() {
        val input = listOf(
            "$ cd /",
            "$ ls",
            "dir a",
            "14848514 b.txt",
            "8504156 c.dat",
            "dir d",
            "$ cd a",
            "$ ls",
            "dir e",
            "29116 f",
            "2557 g",
            "62596 h.lst",
            "$ cd e",
            "$ ls",
            "584 i",
            "$ cd ..",
            "$ cd ..",
            "$ cd d",
            "$ ls",
            "4060174 j",
            "8033020 d.log",
            "5626152 d.ext",
            "7214296 k"
        )
        val parser = FileSystemParser(input)
        parser.processCommands()
        val node = parser.getRootNode()
        assertNotNull(node)
        println(node.toString(0))
        assertEquals(95437, getSumDirectoriesAtMost(node as FolderNode, 100000))
    }
}