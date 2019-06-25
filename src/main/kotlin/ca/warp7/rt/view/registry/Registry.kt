package ca.warp7.rt.view.registry

import java.io.File

object Registry {

    val map: MutableMap<String, String> = mutableMapOf()

    private val home = File(System.getProperty("user.home"))

    private val registryFile = File(home, ".rt-registry.txt")

    fun parse(lines: List<String>) {
        map.clear()
        lines.map { it.trim() }
                .filter { it.isNotEmpty() && !it.startsWith("//") && it.contains("=") }
                .map { it.split("=") }
                .associateTo(map) { it[0].trim() to it[1].trim() }
    }

    fun join(): String {
        return map.entries.joinToString("\n\n") { "${it.key}=${it.value}" }
    }

    fun save() {
        registryFile.writeText(join())
    }

    fun load() {
        registryFile.createNewFile()
        val data = registryFile.readLines()
        parse(data)
    }
}