package me.spb.hse.nikolyukin.cli.shell.executable

import me.spb.hse.nikolyukin.cli.shell.ShellException
import picocli.CommandLine
import picocli.CommandLine.Option
import picocli.CommandLine.Parameters
import java.io.InputStream
import java.nio.file.Path

class ExecutionGrep(private val workingDir: Path, args: List<String>) : ExecutionCommand {
    @Option(names = ["-i"])
    private var ignoreCase: Boolean = false

    @Option(names = ["-w"])
    private var wordRegex: Boolean = false

    @Option(names = ["-A"])
    private var afterContext: Int = 0

    @Parameters(paramLabel = "PATTERN", index = "0")
    private lateinit var patternString: String

    @Parameters(paramLabel = "FILE", index = "1", arity = "0..1")
    private var filePath: Path? = null

    private val opt: Set<RegexOption>

    init {
        CommandLine(this).parseArgs(*args.toTypedArray())
        if (afterContext < 0) {
            throw ShellException("grep: $afterContext: invalid context length argument")
        }
        opt = sequence {
            if (ignoreCase) yield(RegexOption.IGNORE_CASE)
        }.toSet()

        if (wordRegex) {
            patternString = "\\b$patternString\\b"
        }
    }

    override fun execute(stdin: InputStream): OutChannels {
        val outBuilder = mutableListOf<String>()
        val regex = patternString.toRegex(opt)
        val inputReader = filePath?.let { workingDir.resolve(it).toFile().bufferedReader() } ?: stdin.bufferedReader()
        inputReader.useLines {
            var contextLeft = 0
            it.forEach { s ->
                if (regex.find(s) != null) {
                    outBuilder.add(s)
                    contextLeft = afterContext
                } else if (contextLeft > 0) {
                    outBuilder.add(s)
                    contextLeft--
                }
            }
        }
        return OutChannels(outBuilder.joinToString("\n").byteInputStream(), "".byteInputStream())
    }
}