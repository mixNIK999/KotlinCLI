package me.spb.hse.nikolyukin.cli.shell.executable

import me.spb.hse.nikolyukin.cli.shell.ShellStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ExecutionExitTest : ExecutionCommandTest() {
    @Test
    fun execute() {
        ExecutionExit(env).execute("".byteInputStream())
        assertEquals(ShellStatus.STOPPING, env.status)
    }
}