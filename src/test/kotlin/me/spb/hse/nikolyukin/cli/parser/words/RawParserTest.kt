package me.spb.hse.nikolyukin.cli.parser.words

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class RawParserTest {
    @ParameterizedTest
    @MethodSource("provideTestArguments")
    fun `word splitting`(rawString: String, expectedTokens: List<String>) {
        val parser = RawParser()
        assertEquals(expectedTokens, parser.parse(rawString).map { it.text }.toList())
    }

    companion object {
        @JvmStatic
        fun provideTestArguments(): Stream<Arguments> =
            Stream.of(
                Arguments.of("b a", listOf("b", " ", "a")),
                Arguments.of(" a b ", listOf(" ", "a", " ", "b", " ")),
                Arguments.of("foo bar|baz", listOf("foo", " ", "bar", "|", "baz")),
                Arguments.of("""a\"b""", listOf("""a\"b""")),
                Arguments.of(
                    """pre'first | \'second\' | third'post""",
                    listOf("pre", """'first | \'second\' | third'""", "post")
                ),
                Arguments.of("\$bc", listOf("bc")),
                Arguments.of("a\$42\"b\"", listOf("a", "42", "\"b\"")),
                Arguments.of("a=b", listOf("=", " ", "a", " ", "b"))
            )
    }
}