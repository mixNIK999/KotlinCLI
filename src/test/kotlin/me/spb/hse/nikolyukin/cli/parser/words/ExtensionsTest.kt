package me.spb.hse.nikolyukin.cli.parser.words

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.nio.file.Path
import java.util.stream.Stream

internal class ExtensionsTest {
    @ParameterizedTest
    @MethodSource("provideTestArguments")
    fun extending(expression: Extension, inputSeq: List<Word>, expectedSeq: List<String>) {
        assertEquals(expectedSeq, expression.extend(inputSeq.asSequence()).map { it.text }.toList())
    }

    companion object {
        @JvmStatic
        fun provideTestArguments(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    DollarExtension { if (it == "kek") null else it.repeat(2) },
                    listOf(JustWord("lol"), DollarExpression("kek"), DollarExpression("text")),
                    listOf("lol", "texttext")
                ),
                Arguments.of(
                    DollarExtension { it.repeat(2) },
                    listOf(WeakQuotedText("\"a \$b c\"")),
                    listOf("\"a bb c\"")
                ),
                Arguments.of(
                    TildaExtension(Path.of("/my/home")),
                    listOf(JustWord("lol"), HomePath("~/"), JustWord(Path.of("aaa/bbb").toString())),
                    listOf("lol", "/my/home/", "aaa/bbb")
                ),
                Arguments.of(
                    WordSplittingExtension(),
                    listOf(JustWord("a"), JustWord("b"), JustWord("c")),
                    listOf("abc")
                ),
                Arguments.of(
                    WordSplittingExtension(),
                    listOf(FullQuotedText("'ex'"), WeakQuotedText("\"it\"")),
                    listOf("exit")
                ),
                Arguments.of(
                    WordSplittingExtension(),
                    listOf(
                        JustWord("ls"),
                        Spaces(" "),
                        JustWord("-a"),
                        Pipe("|"),
                        JustWord("ca"),
                        FullQuotedText("'t'")
                    ),
                    listOf("ls", " ", "-a", "|", "cat")
                )

            )
    }
}