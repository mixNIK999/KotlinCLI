package me.spb.hse.nikolyukin.cli.shell.executable

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ExecutionEchoTest {

    @Test
    fun executeEmpty() {
        val exe = ExecutionEcho(emptyList())
        val out = exe.execute("".byteInputStream()).stdout.reader().readText()
        assertEquals("", out)
    }


    @Test
    fun executeManyArgs() {
        val exe = ExecutionEcho(listOf("a", "b", "c"))
        val out = exe.execute("".byteInputStream()).stdout.reader().readText()
        assertEquals("a b c", out)
    }
}