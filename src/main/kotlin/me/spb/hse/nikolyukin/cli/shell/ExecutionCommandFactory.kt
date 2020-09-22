package me.spb.hse.nikolyukin.cli.shell

import me.spb.hse.nikolyukin.cli.parser.commands.Command
import me.spb.hse.nikolyukin.cli.shell.executable.ExecutionCommand

interface ExecutionCommandFactory {
    fun createExecutionCommand(environment: Environment, command: Command): ExecutionCommand
}