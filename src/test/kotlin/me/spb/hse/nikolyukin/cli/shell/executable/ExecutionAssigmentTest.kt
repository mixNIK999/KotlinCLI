package me.spb.hse.nikolyukin.cli.shell.executable

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class ExecutionAssigmentTest : ExecutionCommandTest() {

    @Test
    fun addNewVar() {
        val varName = "a"
        val value = "b"

        assertNull(env[varName])
        val exe = ExecutionAssigment(env, varName, value)
        exe.execute(emptyChannel)
        assertEquals(value, env[varName])

        assertEquals(homePath, env[homeVar])
    }

    @Test
    fun rewriteVar() {
        val varName = "a"
        val oldValue = "b"

        env[varName] = oldValue
        assertEquals(oldValue, env[varName])

        val newValue = "c"
        val exe = ExecutionAssigment(env, varName, newValue)
        exe.execute(emptyChannel)
        assertEquals(newValue, env[varName])

        assertEquals(homePath, env[homeVar])
    }

}