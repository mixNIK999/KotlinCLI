package me.spb.hse.nikolyukin.cli.shell.executable

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Files

internal class ExecutionCatTest : ExecutionCommandTest() {

    @Test
    fun executeWithStdin() {
        val text = "abc 123 xyz"
        val stdin = text.byteInputStream()
        val exe = ExecutionCat(homePath, emptyList())
        val out = exe.execute(stdin).stdout.reader().readText()
        assertEquals(text, out)
    }

    @Test
    fun executeWithFile() {

        val text = "abc 123 xyz"
        val file = homePath.resolve("file.txt")
        Files.createFile(file)
        Files.writeString(file, text)

        val exe = ExecutionCat(homePath, listOf(file))

        val out = exe.execute("".byteInputStream()).stdout.reader().readText()
        assertEquals(text, out)
    }
}