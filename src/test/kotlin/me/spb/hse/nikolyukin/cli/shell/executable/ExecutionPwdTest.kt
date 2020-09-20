package me.spb.hse.nikolyukin.cli.shell.executable

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ExecutionPwdTest : ExecutionCommandTest() {

    @Test
    fun execute() {
        val out = ExecutionPwd(env).execute("".byteInputStream()).stdout.reader().readText()
        assertEquals(homePathString, out)
    }
}