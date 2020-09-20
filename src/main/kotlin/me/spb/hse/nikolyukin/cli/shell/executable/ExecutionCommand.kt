package me.spb.hse.nikolyukin.cli.shell.executable

import java.io.InputStream


interface ExecutionCommand {
    // returns stdout and stderr
    fun execute(stdin: InputStream): OutChannels
}

data class OutChannels(val stdout: InputStream, val stderr: InputStream) {
    constructor() : this("".byteInputStream(), "".byteInputStream())
}

