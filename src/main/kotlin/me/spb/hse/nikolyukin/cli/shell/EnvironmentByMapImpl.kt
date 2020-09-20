package me.spb.hse.nikolyukin.cli.shell

import me.spb.hse.nikolyukin.cli.shell.Environment.Companion.WORKING_DIR
import java.nio.file.Path

class EnvironmentByMapImpl(private val map: MutableMap<String, String>) : Environment {
    override fun get(name: String): String? {
        return map[name]
    }

    override fun set(name: String, value: String) {
        map[name] = value
    }

    override fun getWorkingDirectory(): Path {
        return Path.of(map[WORKING_DIR] ?: System.getProperty("user.dir"))
    }

    override fun toMap(): Map<String, String> = map
}