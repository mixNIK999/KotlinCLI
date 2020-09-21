package me.spb.hse.nikolyukin.cli.shell.executable

import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

class ExecutionWc(private val currentPath: Path, private val file: Path) : ExecutionCommand {
    override fun execute(stdin: InputStream): OutChannels {
        val fullPath = currentPath.resolve(file)
        if (!Files.exists(fullPath)) {
            return OutChannels("".byteInputStream(), "Error: file $fullPath does not exist\n".byteInputStream())
        }
        val byteSize = Files.size(fullPath)
        var lineNum = 0
        var wordNum = 0
        fullPath.toFile().useLines { lines ->
            lines.forEach { line ->
                lineNum++
                wordNum += """[^\s]+""".toRegex().findAll(line).count()
            }
        }
        val outString = "$lineNum $wordNum $byteSize\n"
        return OutChannels(outString.byteInputStream(), "".byteInputStream())
    }
}