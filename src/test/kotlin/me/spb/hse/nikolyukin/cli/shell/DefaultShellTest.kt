package me.spb.hse.nikolyukin.cli.shell

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import me.spb.hse.nikolyukin.cli.parser.commands.Command
import me.spb.hse.nikolyukin.cli.parser.commands.CommandParser
import me.spb.hse.nikolyukin.cli.parser.commands.Name
import me.spb.hse.nikolyukin.cli.parser.commands.Pipeline
import me.spb.hse.nikolyukin.cli.shell.executable.ExecutionCommand
import me.spb.hse.nikolyukin.cli.shell.executable.ExecutionExit
import me.spb.hse.nikolyukin.cli.shell.executable.OutChannels
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.InputStream

@ExtendWith(MockKExtension::class)
internal class DefaultShellTest {
    @SpyK
    var environment: Environment = EnvironmentByMapImpl(mutableMapOf())

    @MockK
    lateinit var parser: CommandParser

    @MockK
    lateinit var commandFactory: ExecutionCommandFactory

    @Test
    fun runAndExit() {
        every { parser.parse(any()) } returns Pipeline(listOf(Command(Name("exit"), emptyList())))
        every { commandFactory.createExecutionCommand(any(), any()) } returns ExecutionExit(environment)

        val shell = DefaultShell(environment, parser, commandFactory)

        System.setIn("tixe".byteInputStream())
        shell.run()

        verify(exactly = 1) { parser.parse(any()) }
        verify(exactly = 1) { commandFactory.createExecutionCommand(any(), any()) }

        confirmVerified(parser, commandFactory)
    }

    @Test
    fun run2Lines() {
        val emptyCmd = Command(Name("empty"), emptyList())
        val exitCmd = Command(Name("exit"), emptyList())

        every { parser.parse("string1") } returns Pipeline(listOf(emptyCmd))
        every { parser.parse("string2") } returns Pipeline(listOf(exitCmd))
        every { commandFactory.createExecutionCommand(any(), emptyCmd) } returns ExecutionLambda { "" }
        every { commandFactory.createExecutionCommand(any(), exitCmd) } returns ExecutionExit(environment)

        val shell = DefaultShell(environment, parser, commandFactory)

        System.setIn("string1\nstring2".byteInputStream())
        shell.run()

        verify(exactly = 2) { parser.parse(any()) }
        verify(exactly = 2) { commandFactory.createExecutionCommand(any(), any()) }

        confirmVerified(parser, commandFactory)
    }

    @Test
    fun runPipeline() {
        val text = "bla bla bla\n bla    bla\n"

        val echoCmd = Command(Name("echo"), emptyList())
        val assertCmd = Command(Name("assert"), emptyList())
        val exitCmd = Command(Name("exit"), emptyList())

        val cmdList = listOf(echoCmd, assertCmd, assertCmd, assertCmd, exitCmd)
        every { parser.parse(any()) } returns Pipeline(cmdList)
        every { commandFactory.createExecutionCommand(any(), echoCmd) } returns ExecutionLambda { text }
        every { commandFactory.createExecutionCommand(any(), assertCmd) } returns ExecutionLambda {
            it.also {
                assertEquals(
                    text,
                    it
                )
            }
        }
        every { commandFactory.createExecutionCommand(any(), exitCmd) } returns ExecutionExit(environment)

        val shell = DefaultShell(environment, parser, commandFactory)

        System.setIn("commands".byteInputStream())
        shell.run()

        verify(exactly = 1) { parser.parse(any()) }
        verify(exactly = cmdList.size) { commandFactory.createExecutionCommand(any(), any()) }

        confirmVerified(parser, commandFactory)
    }

    private class ExecutionLambda(private val f: (String) -> String) : ExecutionCommand {
        override fun execute(stdin: InputStream): OutChannels {
            return OutChannels(f(stdin.reader().readText()).byteInputStream(), "".byteInputStream())
        }
    }
}