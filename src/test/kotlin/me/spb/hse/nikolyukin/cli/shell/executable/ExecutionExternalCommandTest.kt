package me.spb.hse.nikolyukin.cli.shell.executable

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files

internal class ExecutionExternalCommandTest : ExecutionCommandTest() {

    @Test
    fun executeCat() {
        val fileName = "file.txt"
        val file = homePath.resolve(fileName)
        Files.createFile(file)

        val text = "super test\n1\n2\n3"
        Files.writeString(file, text)

        val command = if (System.getProperty("os.name").startsWith("windows", ignoreCase = true)) "type" else "cat"
        val cmd = ExecutionExternalCommand(env, command, listOf(fileName))

        val out = cmd.execute("".byteInputStream()).stdout.reader().readText()

        assertEquals(text, out)
    }
}