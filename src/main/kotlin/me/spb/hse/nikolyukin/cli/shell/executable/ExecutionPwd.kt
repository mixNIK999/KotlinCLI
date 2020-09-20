package me.spb.hse.nikolyukin.cli.shell.executable

import me.spb.hse.nikolyukin.cli.shell.Environment
import java.io.InputStream

class ExecutionPwd(private val environment: Environment) : ExecutionCommand {
    override fun execute(stdin: InputStream): OutChannels {
        return OutChannels(environment.getWorkingDirectory().toString().byteInputStream(), "".byteInputStream())
    }
}