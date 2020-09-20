package me.spb.hse.nikolyukin.cli.shell

import me.spb.hse.nikolyukin.cli.shell.Environment.Companion.SHELL_STATUS
import me.spb.hse.nikolyukin.cli.shell.Environment.Companion.WORKING_DIR
import java.nio.file.Path

class EnvironmentByMapImpl(private val map: MutableMap<String, String>) : Environment {
    override fun get(name: String): String? {
        return map[name]
    }

    override fun set(name: String, value: String) {
        map[name] = value
    }

    override fun setWorkingDirectory(dir: Path) {
        map[WORKING_DIR] = dir.toAbsolutePath().toString()
    }

    override fun getWorkingDirectory(): Path {
        return Path.of(map[WORKING_DIR] ?: System.getProperty("user.dir"))
    }

    override fun getStatus(): ShellStatus {
        return ShellStatus.valueOf(map[SHELL_STATUS] ?: ShellStatus.RUNNING.name)
    }

    override fun setStatus(status: ShellStatus) {
        map[SHELL_STATUS] = status.name
    }

    override fun toMap(): Map<String, String> = map
}