package me.spb.hse.nikolyukin.cli.shell.executable

import kotlinx.coroutines.channels.Channel
import me.spb.hse.nikolyukin.cli.shell.Environment
import me.spb.hse.nikolyukin.cli.shell.EnvironmentByMapImpl
import org.junit.jupiter.api.BeforeEach

open class ExecutionCommandTest {
    protected lateinit var env: Environment
    protected val emptyChannel = Channel<String>(0).apply { close() }
    protected val homeVar = "HOME"
    protected val homePath = "/home/mishock"

    @BeforeEach
    fun init() {
        env = EnvironmentByMapImpl(mutableMapOf())
        env[homeVar] = homePath
    }
}