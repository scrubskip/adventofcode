package day17

import java.lang.Integer.max

fun main() {
    // target area: x=56..76, y=-162..-134
    val target = TargetArea(56..76, -162..-134)
    println(findMaxY(target))
}

fun findMaxY(area: TargetArea): Int {
    var allMaxY = 0
    for (x in 0..max(area.xRange.first, area.xRange.last)) {
        for (y in area.yRange.first..500) {
            with(runTest(area, StepData.startWithVelocity(x, y))) {
                if (resultType == Result.Type.ON_TARGET) {
                    if (maxY > allMaxY) {
                        allMaxY = maxY
                    }
                }
            }
        }
    }

    return allMaxY
}

fun countValid(area: TargetArea): Int {
    var count = 0
    for (x in 0..max(area.xRange.first, area.xRange.last)) {
        // Now need to find the right Y
        for (y in area.yRange.first..500) {
            with(runTest(area, StepData.startWithVelocity(x, y))) {
                if (resultType == Result.Type.ON_TARGET) {
                    count++
                }
            }
        }
    }
    return count
}

fun runTest(area: TargetArea, initial: StepData): Result {
    var currentStep = initial
    var maxY = 0
    while (true) {
        if (area.contains(currentStep.position)) {
            return Result(Result.Type.ON_TARGET, maxY)
        } else if (area.isOvershoot(currentStep)) {
            return Result(Result.Type.OVERSHOOT, maxY)
        } else if (area.isUndershoot(currentStep)) {
            return Result(Result.Type.UNDERSHOOT, maxY)
        }

        currentStep = currentStep.getNextStep()
        if (currentStep.position.second > maxY) maxY = currentStep.position.second
    }
}

data class StepData(var position: Pair<Int, Int>, var velocity: Pair<Int, Int>) {

    fun getNextStep(): StepData {
        val newPosition = Pair(position.first + velocity.first, position.second + velocity.second)
        val newVelocityX = when {
            velocity.first > 0 -> velocity.first - 1
            velocity.first < 0 -> velocity.first + 1
            else -> 0
        }

        return StepData(newPosition, Pair(newVelocityX, velocity.second - 1))
    }

    companion object {
        fun startWithVelocity(velocityX: Int, velocityY: Int): StepData {
            return StepData(Pair(0, 0), Pair(velocityX, velocityY))
        }
    }
}

class Result(val resultType: Type, val maxY: Int) {
    enum class Type {
        UNDERSHOOT, ON_TARGET, OVERSHOOT
    }
}

data class TargetArea(val xRange: IntRange, val yRange: IntRange) {
    fun contains(point: Pair<Int, Int>): Boolean {
        return point.first in xRange && point.second in yRange
    }

    fun isOvershoot(stepData: StepData): Boolean {
        // we are past target area if the x and y are outside the max range for x and the min range for y
        return stepData.position.first > xRange.last && stepData.position.second < yRange.first
    }

    fun isUndershoot(stepData: StepData): Boolean {
        return stepData.position.first <= xRange.last && stepData.position.second < yRange.first
    }
}