package me.spb.hse.nikolyukin.cli.shell.executable

import java.io.InputStream

class ExecutionEcho(private val args: List<String>) : ExecutionCommand {
    override fun execute(stdin: InputStream): OutChannels {
        val argsStr = args.joinToString(separator = " ")
        return OutChannels(argsStr.byteInputStream(), "".byteInputStream())
    }
}