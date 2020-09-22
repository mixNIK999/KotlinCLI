package me.spb.hse.nikolyukin.cli.shell

import me.spb.hse.nikolyukin.cli.parser.commands.Command
import me.spb.hse.nikolyukin.cli.shell.executable.*
import java.nio.file.Path


class DefaultExecutionCommandFactory(private val environment: Environment) : ExecutionCommandFactory {
    override fun createExecutionCommand(command: Command): ExecutionCommand =
        when (command.name.text) {
            "=" -> {
                if (command.args.size != 2) {
                    throw ShellException("assigment takes exactly 2 arguments, but was found ${command.args}")
                }
                ExecutionAssigment(environment, command.args[0].value, command.args[1].value)
            }
            "cat" -> ExecutionCat(environment.workingDir, command.args.map { Path.of(it.value) })
            "echo" -> ExecutionEcho(command.args.map { it.value })
            "exit" -> ExecutionExit(environment)
            "pwd" -> ExecutionPwd(environment)
            "wc" -> {
                if (command.args.isEmpty()) {
                    throw ShellException("wc takes 1 argument, but was found 0")
                }
                ExecutionWc(environment.workingDir, Path.of(command.args[0].value))
            }
            else -> {
                ExecutionExternalCommand(environment, command.name.text, command.args.map { it.value })
            }
        }
}