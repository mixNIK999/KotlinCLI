package me.spb.hse.nikolyukin.cli.shell.executable

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Path

class ExecutionCat(private val currentPath: Path, private val files: List<Path>) : ExecutionCommand {
    override suspend fun execute(stdin: ReceiveChannel<String>): OutChannels {
        val stdout = Channel<String>()
        val stderr = Channel<String>()
        if (files.isEmpty()) {
            for (str in stdin) {
                stdout.send(str)
            }
        } else {
            for (file in files) {
                val pathToFile = currentPath.resolve(file)
                if (!Files.exists(pathToFile)) {
                    stderr.send("File $pathToFile does not exists")
                    continue
                }
                withContext(Dispatchers.IO) {
                    // if I got it right Dispatcher.IO is right way to call blocking method
                    @Suppress("BlockingMethodInNonBlockingContext")
                    stdout.send(Files.readString(pathToFile))
                }
            }
        }
        return OutChannels(stdout, stderr)
    }
}