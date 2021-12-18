package day17

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

internal class Day17KtTest {

    val TEST_TARGET = TargetArea(20..30, -10..-5)

    @Test
    fun testExample() {
        with(runTest(TEST_TARGET, StepData.startWithVelocity(7, 2))) {
            assertEquals(Result.Type.ON_TARGET, resultType)
        }
        with(runTest(TEST_TARGET, StepData.startWithVelocity(6, 3))) {
            assertEquals(Result.Type.ON_TARGET, resultType)
        }
        with(runTest(TEST_TARGET, StepData.startWithVelocity(6, 10))) {
            assertEquals(Result.Type.UNDERSHOOT, resultType)
        }
        with(runTest(TEST_TARGET, StepData.startWithVelocity(9, 0))) {
            assertEquals(Result.Type.ON_TARGET, resultType)
        }
        with(runTest(TEST_TARGET, StepData.startWithVelocity(17, -4))) {
            assertEquals(Result.Type.OVERSHOOT, resultType)
        }
        with(runTest(TEST_TARGET, StepData.startWithVelocity(6, 9))) {
            assertEquals(Result.Type.ON_TARGET, resultType)
            assertEquals(45, maxY)
        }

        assertEquals(45, findMaxY(TEST_TARGET))
        assertEquals(112, countValid(TEST_TARGET))
    }

    @Test
    fun testPart1() {
        val target = TargetArea(56..76, -162..-134)
        println(findMaxY(target))
        println(countValid(target))
    }
}