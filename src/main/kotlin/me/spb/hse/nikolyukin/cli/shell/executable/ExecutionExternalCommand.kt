package me.spb.hse.nikolyukin.cli.shell.executable

import java.io.InputStream
import java.nio.file.Path

class ExecutionExternalCommand(
    private val workingDir: Path,
    private val name: String,
    private val args: List<String>
) : ExecutionCommand {

    override fun execute(stdin: InputStream): OutChannels {
        val cmdPath = workingDir.resolve(name)
        val process = ProcessBuilder(listOf(cmdPath.toString()))
            .directory(workingDir.toFile())
            .start()

        val inStream = process.outputStream.bufferedWriter()
        val outStream = process.inputStream.bufferedReader()
        val errStream = process.errorStream.bufferedReader()

        return OutChannels()
    }
}