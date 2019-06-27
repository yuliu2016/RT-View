package ca.warp7.rt.view.window

import java.io.File

object Registry {

    private val map: MutableMap<String, String> = mutableMapOf()

    private val home = System.getProperty("user.home").replace(File.separatorChar, '/')
    private const val version = "RT2019"

    private val registryFile = File(home, ".rt-registry.txt")

    @Suppress("SpellCheckingInspection")
    private val defaultProps = mapOf(
            "USERPATH" to home,
            "VERSION_PREFIX" to version,
            "SYSTEM_PATH" to "$home/$version/System"
    )

    fun parse(lines: List<String>) {
        map.clear()
        lines.map { it.trim() }
                .filter { it.isNotEmpty() && !it.startsWith("//") && it.contains("=") }
                .map { it.split("=") }
                .associateTo(map) { it[0].trim() to it[1].trim() }
    }

    operator fun get(key: String): String? {
        return map[key]?.run {
            var replaced = this
            defaultProps.forEach {
                replaced = replaced.replace("{${it.key}}", it.value)
            }
            replaced
        }
    }

    operator fun set(key: String, value: String) {
        map[key] = value
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