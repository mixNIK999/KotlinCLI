package me.spb.hse.nikolyukin.cli.shell.executable

import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

class ExecutionWc(private val currentPath: Path, private val file: Path?) : ExecutionCommand {
    override fun execute(stdin: InputStream): OutChannels {
        val inputReader = if (file == null) {
            stdin.bufferedReader()
        } else {
            val fullPath = currentPath.resolve(file)
            if (!Files.exists(fullPath)) {
                return OutChannels("".byteInputStream(), "Error: file $fullPath does not exist".byteInputStream())
            }
            fullPath.toFile().bufferedReader()
        }
        var byteSize = 0
        var lineNum = 0
        var wordNum = 0

        inputReader.useLines { lines ->
            lines.forEach { line ->
                lineNum++
                wordNum += """[^\s]+""".toRegex().findAll(line).count()
                byteSize += line.toByteArray().size + 1
            }
        }
        // we counts \n at the end of a file
        if (byteSize > 0) byteSize--
        val outString = "$lineNum $wordNum $byteSize\n"
        return OutChannels(outString.byteInputStream(), "".byteInputStream())
    }
}