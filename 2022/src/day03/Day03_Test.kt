package day03

import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class Day03Test {
    @Test
    fun testGetItemPriority() {
        assertEquals('p', getItemType("vJrwpWtwJgWrhcsFMMfFFhFp"))
        assertEquals(16, getPriority('p'))

        assertEquals('L', getItemType("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"))
        assertEquals(38, getPriority('L'))
    }
}
