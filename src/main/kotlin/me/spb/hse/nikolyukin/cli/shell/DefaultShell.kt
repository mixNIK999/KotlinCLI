package me.spb.hse.nikolyukin.cli.shell

import me.spb.hse.nikolyukin.cli.parser.commands.CommandParser
import java.io.InputStream

class DefaultShell(
    private val environment: Environment,
    private val parser: CommandParser,
    private val commandFactory: ExecutionCommandFactory
) : Shell {
    override fun run() {
        while (environment.status == ShellStatus.RUNNING) {
            val line = readLine() ?: break
            try {
                val pipeline = parser.parse(line)
                pipeline.commands.fold("".byteInputStream()) { stdin: InputStream, command ->
                    val exe = commandFactory.createExecutionCommand(command)
                    val out = exe.execute(stdin)
                    println(out.stderr.bufferedReader().readText())
                    out.stdout
                }
            } catch (ex: Exception) {
                println(ex.message)
            }
        }
    }
}