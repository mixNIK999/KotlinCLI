package me.spb.hse.nikolyukin.cli.shell

import java.nio.file.Path

interface Environment {
    operator fun get(name: String): String?
    operator fun set(name: String, value: String)

    fun getWorkingDirectory(): Path
    fun toMap(): Map<String, String>

    companion object {
        const val WORKING_DIR = "WORKING_DIR"
    }
}