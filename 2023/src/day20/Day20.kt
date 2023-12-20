package day20

import java.io.File

fun main() {
    val input = File("src/day20/day20.txt").readLines()

    val pulseSenderCount = PulseSender(input)
    pulseSenderCount.printSend = false
    repeat(1000) { pulseSenderCount.pressButton() }
    println(pulseSenderCount.lowCount * pulseSenderCount.highCount)
}

enum class Pulse {
    LOW,
    HIGH
}

data class PulseMessage(val from: String, val to: String, val pulse: Pulse)

class PulseSender(input: List<String>) {
    val modules: Map<String, Module>
    val pulses = mutableListOf<PulseMessage>()
    var lowCount = 0L
    var highCount = 0L
    var printSend = true

    init {
        modules = parseModules(input)
    }

    private fun parseModules(input: List<String>): Map<String, Module> {
        // First loop through and make all the modules
        val moduleMap = mutableMapOf<String, Module>()

        val parts = input.map { it.split(" -> ") }
        parts.forEach {
            val module = it[0]
            if ("broadcaster" == module) {
                moduleMap[module] = Broadcaster(module)
            } else if ("output" == module) {
                moduleMap[module] = Output(module)
            } else {
                val name = module.drop(1)
                if (module.startsWith("%")) {
                    moduleMap[name] = FlipFlop(name)
                } else if (module.startsWith("&")) {
                    moduleMap[name] = Conjunction(name)
                }
            }
        }

        // Now loop back and wire them up
        parts.forEach {
            val moduleName = it[0].replace("&", "").replace("%", "")
            it[1].split(",")
                .map { destinationName -> destinationName.trim() }
                .forEach { destination ->
                    moduleMap[moduleName]!!.addModule(
                        moduleMap.getOrPut(
                            destination
                        ) { Output(destination) }
                    )
                }
        }

        return moduleMap.toMap()
    }

    fun sendPulse(msg: PulseMessage) {
        val module = modules[msg.to]!!
        if (msg.pulse == Pulse.LOW) {
            lowCount++
        } else if (msg.pulse == Pulse.HIGH) {
            highCount++
        }
        if (printSend) {
            println("${msg.from} ${msg.pulse} -> ${msg.to}")
        }
        if (module.shouldPropagate(msg.pulse)) {
            val pulseToSend = module.processPulse(msg.pulse, msg.from)
            module.destinationModules.forEach {
                pulses.add(PulseMessage(module.name, it.name, pulseToSend))
            }
        }
        // Now that pulses are queued, process
        while (pulses.isNotEmpty()) {
            sendPulse(pulses.removeAt(0))
        }
    }

    fun pressButton() {
        sendPulse(PulseMessage("button", "broadcaster", Pulse.LOW))
    }
}

abstract class Module(val name: String) {

    val destinationModules = mutableListOf<Module>()
    val inputModules = mutableListOf<Module>()

    fun addModule(module: Module) {
        destinationModules.add(module)
        module.inputModules.add(this)
    }

    abstract fun processPulse(pulse: Pulse, from: String): Pulse

    open fun shouldPropagate(pulse: Pulse): Boolean {
        return true
    }
}

class Broadcaster(name: String) : Module(name) {
    override fun processPulse(pulse: Pulse, from: String): Pulse {
        return pulse
    }
}

class FlipFlop(name: String) : Module(name) {
    private var state = false
    override fun processPulse(pulse: Pulse, from: String): Pulse {
        if (pulse == Pulse.LOW) {
            state = !state
        }
        return if (state) Pulse.HIGH else Pulse.LOW
    }

    override fun shouldPropagate(pulse: Pulse): Boolean {
        return pulse == Pulse.LOW
    }
}

class Conjunction(name: String) : Module(name) {
    private val lastPulse = mutableMapOf<String, Pulse>()

    override fun processPulse(pulse: Pulse, from: String): Pulse {
        lastPulse[from] = pulse
        return if (inputModules.all { lastPulse.getOrDefault(it.name, Pulse.LOW) == Pulse.HIGH })
            Pulse.LOW else Pulse.HIGH
    }
}

class Output(name: String) : Module(name) {
    override fun processPulse(pulse: Pulse, from: String): Pulse {
        return pulse
    }
}