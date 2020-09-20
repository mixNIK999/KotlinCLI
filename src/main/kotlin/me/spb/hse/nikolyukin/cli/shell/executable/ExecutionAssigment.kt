package me.spb.hse.nikolyukin.cli.shell.executable

import me.spb.hse.nikolyukin.cli.shell.Environment
import java.io.InputStream

class ExecutionAssigment(private val env: Environment, private val variable: String, private val value: String) :
    ExecutionCommand {
    override fun execute(stdin: InputStream): OutChannels {
        env[variable] = value
        return OutChannels()
    }
}