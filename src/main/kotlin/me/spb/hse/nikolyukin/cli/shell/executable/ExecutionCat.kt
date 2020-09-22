package me.spb.hse.nikolyukin.cli.shell.executable

import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

class ExecutionCat(private val currentPath: Path, private val files: List<Path>) : ExecutionCommand {
    override fun execute(stdin: InputStream): OutChannels {
        val stdoutBuff = mutableListOf<Byte>()
        val stderrBuilder = mutableListOf<String>()
        if (files.isEmpty()) {
            stdoutBuff += stdin.readAllBytes().toList()
        } else {
            for (file in files) {
                val pathToFile = currentPath.resolve(file)
                if (!Files.exists(pathToFile)) {
                    stderrBuilder.add("Error: file $pathToFile does not exist")
                    continue
                }
                stdoutBuff += Files.readAllBytes(pathToFile).toList()
            }
        }
        return OutChannels(
            stdoutBuff.toByteArray().inputStream(),
            stderrBuilder.joinToString(separator = "\n").byteInputStream()
        )
    }
}