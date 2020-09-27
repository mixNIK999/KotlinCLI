package me.spb.hse.nikolyukin.cli.parser.commands

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import me.spb.hse.nikolyukin.cli.parser.words.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@ExtendWith(MockKExtension::class)
internal class WordToCommandParserAdapterTest {
    @MockK
    lateinit var wordParser: WordParser

    @ParameterizedTest
    @MethodSource("provideTestArguments")
    fun parseTest(wordParserOutput: Sequence<Word>, expectedPipeline: Pipeline) {
        val adapter = WordToCommandParserAdapter(wordParser)
        every { wordParser.parse(any()) } returns wordParserOutput
        assertEquals(expectedPipeline, adapter.parse("something"))
    }

    companion object {
        @JvmStatic
        fun provideTestArguments(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    sequenceOf(JustWord("echo"), JustWord("foo")),
                    Pipeline(listOf(Command(Name("echo"), listOf(Argument("foo")))))
                ),
                Arguments.of(
                    sequenceOf(JustWord("echo"), JustWord("foo"), Pipe("|"), Spaces(" "), JustWord("cat")),
                    Pipeline(listOf(Command(Name("echo"), listOf(Argument("foo"))), Command(Name("cat"), emptyList())))
                )

            )
    }
}