package me.spb.hse.nikolyukin.cli.shell

import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import me.spb.hse.nikolyukin.cli.parser.commands.CommandParser
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class DefaultShellTest {
    @SpyK
    var environment: Environment = EnvironmentByMapImpl(mutableMapOf())

    @MockK
    lateinit var parser: CommandParser

    @MockK
    lateinit var commandFactory: ExecutionCommandFactory

    @Test
    fun run() {
        val shell = DefaultShell(environment, parser, commandFactory)
    }
}