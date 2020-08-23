package me.spb.hse.nikolyukin.cli.parser.commands

import me.spb.hse.nikolyukin.cli.parser.words.Pipe
import me.spb.hse.nikolyukin.cli.parser.words.RawParser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class PipelineBuilderTest {
    @ParameterizedTest
    @MethodSource("provideTestArguments")
    fun `pipeline building`(words: List<String?>, expectedPipeline: Pipeline) {
        val builder = PipelineBuilder()
        words.forEach { word -> word?.let { builder.addWord(it) } ?: builder.addPipe() }
        assertEquals(expectedPipeline, builder.toPipeline())
    }

    companion object {
        @JvmStatic
        fun provideTestArguments(): Stream<Arguments> =
            Stream.of(
                Arguments.of(listOf("echo", "lol"), Pipeline(listOf(CommandEchoLol))),
                Arguments.of(listOf("cat"), Pipeline(listOf(CommandCat))),
                Arguments.of(listOf("tee", "-a", "log.txt"), Pipeline(listOf(CommandTee))),
                Arguments.of(
                    listOf("echo", "lol", null, "tee", "-a", "log.txt", null, "cat"),
                    Pipeline(listOf(CommandEchoLol, CommandTee, CommandCat))
                ),
                Arguments.of(
                    listOf(null, null,"echo", "lol", null, null, null, "cat", null, null),
                    Pipeline(listOf(CommandEchoLol, CommandCat))
                )
            )

        private val CommandEchoLol = Command(Name("echo"), listOf(Argument("lol")))
        private val CommandCat = Command(Name("cat"), emptyList())
        private val CommandTee = Command(Name("tee"), listOf(Argument("-a"), Argument("log.txt")))
    }
}