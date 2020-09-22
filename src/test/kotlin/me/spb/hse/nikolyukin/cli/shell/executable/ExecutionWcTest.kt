package me.spb.hse.nikolyukin.cli.shell.executable

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path

internal class ExecutionWcTest : ExecutionCommandTest() {

    @Test
    fun executeWithEmptyFile() {
        val file = Path.of("file.txt")
        Files.createFile(homePath.resolve(file))
        val exe = ExecutionWc(homePath, file)
        val out = exe.execute("".byteInputStream()).stdout.reader().readText()
        assertEquals("0 0 0\n", out)
    }

    @Test
    fun executeWithManySpacesAndNewLines() {
        val file = Path.of("file.txt")
        Files.createFile(homePath.resolve(file))
        val text = "1 2 \n\n 3   4   5\n 6"
        Files.writeString(homePath.resolve(file), text)
        val exe = ExecutionWc(homePath, file)
        val out = exe.execute("".byteInputStream()).stdout.reader().readText()
        assertEquals("4 6 ${text.toByteArray().size}\n", out)
    }
}