package me.spb.hse.nikolyukin.cli.shell

import java.nio.file.Path

interface Environment {
    operator fun get(name: String): String?
    operator fun set(name: String, value: String)

    var status: ShellStatus
    var workingDir: Path

    fun toMap(): Map<String, String>

    companion object {
        //absolute path to working directory
        const val WORKING_DIR = "_WORKING_DIR"
        const val SHELL_STATUS = "_IS_SHELL_RUNNING"
    }
}

enum class ShellStatus {
    RUNNING,
    STOPPING
}