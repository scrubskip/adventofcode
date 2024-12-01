package day19

import java.io.File
import java.lang.IllegalArgumentException

fun main() {
    val input = File("src/day19/day19.txt").readLines()
    val workflowStrings = input.takeWhile { it.isNotEmpty() }
    val partStrings = input.takeLastWhile { it.isNotEmpty() }

    val workflows = getWorkflows(workflowStrings)
    val parts = partStrings.map{Part.parsePart(it)}

    println(getSumTotalAccept(parts,workflows))
}

fun getSumTotalAccept(parts: List<Part>, workflows: Map<String, Workflow>) : Int {
    val startFlow = workflows["in"]

    return parts.filter {startFlow?.isAccepted(it, workflows) == Status.ACCEPT}.sumOf {part -> part.getTotalValue()}
}
data class Part(val x : Int, val m : Int, val a : Int, val s : Int) {
    companion object {
        private val PART = Regex("""\{x=(\d+),m=(\d+),a=(\d+),s=(\d+)\}""")
        fun parsePart(input : String) : Part {
            val match = PART.find(input)
            return if (match != null) {
                val (x, m, a, s) = match.destructured
                return Part(x.toInt(), m.toInt(), a.toInt(), s.toInt())
            } else {
                throw  IllegalArgumentException("invalid part input $input")
            }
        }
    }

    fun getValue(category : String) :Int {
        return when (category) {
            "x" -> x
            "m" -> m
            "a" -> a
            "s" -> s
            else -> throw IllegalArgumentException("Unknown category $category")
        }
    }

    fun getTotalValue() : Int {
        return x + m + a + s
    }
}

enum class Status {
    UNKNOWN,
    ACCEPT,
    REJECT
}

abstract class Rule {
    abstract fun checkRule(part: Part, workflows : Map<String, Workflow>) : Status
}

class RejectRule : Rule() {
    override fun checkRule(part: Part, workflows : Map<String, Workflow>) : Status {
        return Status.REJECT
    }
}

class AcceptRule : Rule() {
    override fun checkRule(part: Part, workflows : Map<String, Workflow>) : Status {
        return Status.ACCEPT
    }
}

class NameRule(val name : String) : Rule() {
    override fun checkRule(part: Part, workflows : Map<String, Workflow>) : Status {
        return workflows[name]!!.isAccepted(part, workflows)
    }
}
class ThresholdRule(val category : String, val threshold : Int, val greater : Boolean,
           val resultTrue : String) : Rule() {

    companion object {
        private val RULE = Regex("""(\w)([<>]+)(\d+):(\w+)""")
        fun parseRule(input : String) : ThresholdRule {
            val match = RULE.find(input)
            if (match != null) {
                val (cat, great, thresh, result) = match.destructured
                return ThresholdRule(cat, thresh.toInt(), great == ">", result)
            } else {
                throw IllegalArgumentException("Invalid threshold rule $input")
            }
        }
    }
    override fun checkRule(part: Part, workflows : Map<String, Workflow>) : Status {
        val catValue = part.getValue(category)
        val thresholdMet = (greater && catValue > threshold) || (!greater && catValue < threshold)
        return if (thresholdMet) {
            when (resultTrue) {
                "R" -> Status.REJECT
                "A" -> Status.ACCEPT
                else -> workflows[resultTrue]!!.isAccepted(part, workflows)
            }
        } else {
            Status.UNKNOWN
        }
    }
}


class Workflow(val input: String) {
    val name : String
    val rules : List<Rule>

    init {
        name = input.takeWhile { it != '{' }
        val ruleStrings = input.substring(name.length + 1, input.length - 1).split(",")
        val parseRules = ruleStrings.map{
            when (it) {
                "R" -> RejectRule()
                "A" -> AcceptRule()
                else -> if (it.contains(":")) ThresholdRule.parseRule(it) else NameRule(it)
            }
        }
        rules = parseRules.toList()
    }

    fun isAccepted(part:Part, workflows: Map<String, Workflow>) : Status {
        rules.forEach {
            when (it.checkRule(part, workflows)) {
                Status.ACCEPT -> return Status.ACCEPT
                Status.REJECT -> return Status.REJECT
                else -> {}
            }
        }
        return Status.UNKNOWN
    }

}

fun getWorkflows(input: List<String>) : Map<String, Workflow> {
    return input.map {Workflow(it)}.associateBy { it.name }
}