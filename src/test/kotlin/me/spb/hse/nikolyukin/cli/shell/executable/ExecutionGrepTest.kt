package me.spb.hse.nikolyukin.cli.shell.executable

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path

internal class ExecutionGrepTest : ExecutionCommandTest() {

    private lateinit var file: Path
    private val fileName = "file.txt"

    @BeforeEach
    fun init() {
        file = homePath.resolve(fileName)
        Files.createFile(file)
    }

    @Test
    fun executeWithFileAndIWOptins() {
        val lines = listOf("0) a", "1) A", "2) abc")
        val text = lines.joinToString("\n")
        Files.writeString(file, text)
        val exe = ExecutionGrep(homePath, listOf("a", "-i", "-w", fileName))
        val out = exe.execute("".byteInputStream()).stdout.bufferedReader().readText()
        assertEquals("${lines[0]}\n${lines[1]}", out)
    }

    @Test
    fun executeWithPipeAndContext() {
        val lines = listOf("0) text0", "1) text1", "2) text2", "3) text3")
        val text = lines.joinToString("\n")
        val exe = ExecutionGrep(homePath, listOf("text1", "-A", "1"))
        val out = exe.execute(text.byteInputStream()).stdout.bufferedReader().readText()
        assertEquals("${lines[1]}\n${lines[2]}", out)
    }
}