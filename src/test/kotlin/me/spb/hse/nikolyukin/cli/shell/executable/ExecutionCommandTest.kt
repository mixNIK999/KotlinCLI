package me.spb.hse.nikolyukin.cli.shell.executable

import me.spb.hse.nikolyukin.cli.shell.Environment
import me.spb.hse.nikolyukin.cli.shell.EnvironmentByMapImpl
import me.spb.hse.nikolyukin.cli.shell.ShellStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

open class ExecutionCommandTest {
    protected lateinit var env: Environment
    @TempDir
    protected lateinit var homePath: Path
    protected val homeVar = "HOME"
    protected lateinit var homePathString: String

    @BeforeEach
    fun init() {
        env = EnvironmentByMapImpl(mutableMapOf())
        env.setStatus(ShellStatus.RUNNING)
        env.setWorkingDirectory(homePath)

        homePathString = homePath.toAbsolutePath().toString()
        env[homeVar] = homePathString
    }
}