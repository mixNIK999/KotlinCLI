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
                val pipelineOut = pipeline.commands.fold("".byteInputStream()) { stdin: InputStream, command ->
                    val exe = commandFactory.createExecutionCommand(environment, command)
                    val out = exe.execute(stdin)
                    val errMsg = out.stderr.bufferedReader().readText()
                    if (errMsg.isNotEmpty()) {
                        println(errMsg)
                    }
                    out.stdout
                }
                val outText = pipelineOut.bufferedReader().readText()
                if (outText.isNotEmpty())
                    println(outText)
            } catch (ex: Exception) {
                println("Error: ${ex.message}")
            }
        }
    }
}