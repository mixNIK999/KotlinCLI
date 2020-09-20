package me.spb.hse.nikolyukin.cli.shell.executable

import kotlinx.coroutines.channels.ReceiveChannel
import me.spb.hse.nikolyukin.cli.shell.Environment

class ExecutionAssigment(private val env: Environment, private val variable: String, private val value: String) :
    ExecutionCommand {
    override suspend fun execute(stdin: ReceiveChannel<String>): OutChannels {
        env[variable] = value
        return OutChannels()
    }
}