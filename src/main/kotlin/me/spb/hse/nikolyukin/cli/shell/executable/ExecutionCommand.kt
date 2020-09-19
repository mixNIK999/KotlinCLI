package me.spb.hse.nikolyukin.cli.shell.executable

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

interface ExecutionCommand {
    // returns stdout and stderr
    fun execute(stdin: ReceiveChannel<String>): OutChannels
}

data class OutChannels(val stdout: ReceiveChannel<String>, val stderr: ReceiveChannel<String>) {
    constructor() : this(Channel<String>(0).apply { close() }, Channel<String>(0).apply { close() })
}

