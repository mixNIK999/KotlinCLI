package me.spb.hse.nikolyukin.cli.shell.executable

import me.spb.hse.nikolyukin.cli.shell.Environment
import me.spb.hse.nikolyukin.cli.shell.ShellStatus
import java.io.InputStream

class ExecutionExit(private val environment: Environment) : ExecutionCommand {
    override fun execute(stdin: InputStream): OutChannels {
        environment.setStatus(ShellStatus.STOPPING)
        return OutChannels()
    }
}