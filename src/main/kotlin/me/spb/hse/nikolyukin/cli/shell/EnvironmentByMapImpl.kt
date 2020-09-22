package me.spb.hse.nikolyukin.cli.shell

import me.spb.hse.nikolyukin.cli.shell.Environment.Companion.SHELL_STATUS
import me.spb.hse.nikolyukin.cli.shell.Environment.Companion.WORKING_DIR
import java.nio.file.Path

class EnvironmentByMapImpl(private val map: MutableMap<String, String>) : Environment {

    init {
        map.putIfAbsent(WORKING_DIR, System.getProperty("user.dir"))
        map.putIfAbsent(SHELL_STATUS, ShellStatus.RUNNING.name)
    }

    override fun get(name: String): String? {
        return map[name]
    }

    override fun set(name: String, value: String) {
        map[name] = value
    }

    override var status: ShellStatus
        get() = ShellStatus.valueOf(map[SHELL_STATUS] ?: ShellStatus.RUNNING.name)
        set(value) {
            map[SHELL_STATUS] = value.name
        }
    override var workingDir: Path
        get() = Path.of(map[WORKING_DIR] ?: System.getProperty("user.dir"))
        set(value) {
            map[WORKING_DIR] = value.toAbsolutePath().toString()
        }

    override fun toMap(): Map<String, String> = map
}