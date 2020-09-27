package me.spb.hse.nikolyukin.cli.shell.executable

import me.spb.hse.nikolyukin.cli.shell.Environment
import java.io.InputStream
import java.nio.file.Files

class ExecutionExternalCommand(
    private val environment: Environment,
    private val name: String,
    private val args: List<String>
) : ExecutionCommand {

    override fun execute(stdin: InputStream): OutChannels {
        val workingDir = environment.workingDir
        val cmdPath = workingDir.resolve(name)
        val cmd = if (Files.exists(cmdPath)) cmdPath.toString() else name

        val pBuilder = ProcessBuilder(listOf(cmd) + args).directory(workingDir.toFile())
        val extEnv = pBuilder.environment()
        extEnv.putAll(environment.toMap())
        val process = pBuilder.start()

        val inStream = process.outputStream
        val outStream = process.inputStream
        val errStream = process.errorStream

        stdin.copyTo(inStream)

        return OutChannels(outStream, errStream)
    }
}