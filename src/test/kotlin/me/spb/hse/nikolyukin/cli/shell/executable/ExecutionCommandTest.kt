package me.spb.hse.nikolyukin.cli.shell.executable

import me.spb.hse.nikolyukin.cli.shell.Environment
import me.spb.hse.nikolyukin.cli.shell.EnvironmentByMapImpl
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
        homePathString = homePath.toAbsolutePath().toString()
        env = EnvironmentByMapImpl(mutableMapOf())
        env[homeVar] = homePathString
    }
}