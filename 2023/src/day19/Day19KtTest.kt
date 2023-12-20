package day19

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class Day19KtTest {

    @Test
    fun testParseWorkflow() {
        val workflowString = "px{a<2006:qkq,m>2090:A,rfg}"

        val workflow = Workflow(workflowString)

        assertEquals("px", workflow.name)
        assertEquals(3, workflow.rules.size)
    }

    @Test
    fun testPart() {
        val part = Part.parsePart("{x=787,m=2655,a=1222,s=2876}")

        assertEquals(787, part.x)
        assertEquals(2655, part.m)
        assertEquals(1222, part.a)
        assertEquals(2876, part.s)
    }

    @Test
    fun testExample() {
        val workflowInput = listOf(
            "px{a<2006:qkq,m>2090:A,rfg}",
            "pv{a>1716:R,A}",
            "lnx{m>1548:A,A}",
            "rfg{s<537:gd,x>2440:R,A}",
            "qs{s>3448:A,lnx}",
            "qkq{x<1416:A,crn}",
            "crn{x>2662:A,R}",
            "in{s<1351:px,qqz}",
            "qqz{s>2770:qs,m<1801:hdj,R}",
            "gd{a>3333:R,R}",
            "hdj{m>838:A,pv}",
        )

        val partsInput = listOf(
            "{x=787,m=2655,a=1222,s=2876}",
            "{x=1679,m=44,a=2067,s=496}",
            "{x=2036,m=264,a=79,s=2244}",
            "{x=2461,m=1339,a=466,s=291}",
            "{x=2127,m=1623,a=2188,s=1013}",
        )

        val workflows = getWorkflows(workflowInput)
        val parts = partsInput.map {Part.parsePart(it)}

        val startFlow = workflows["in"]
        assertNotNull(startFlow)

        assertEquals(Status.ACCEPT, startFlow?.isAccepted(parts[0], workflows))
        assertEquals(Status.REJECT, startFlow?.isAccepted(parts[1], workflows))
        assertEquals(Status.ACCEPT, startFlow?.isAccepted(parts[2], workflows))
        assertEquals(Status.REJECT, startFlow?.isAccepted(parts[3], workflows))
        assertEquals(Status.ACCEPT, startFlow?.isAccepted(parts[4], workflows))

        assertEquals(19114, getSumTotalAccept(parts, workflows))
    }
}