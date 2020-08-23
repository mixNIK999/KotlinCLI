package me.spb.hse.nikolyukin.cli.parser.words

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class WordParserWithExtensionTest {
    @MockK
    lateinit var initParser: WordParser

    @MockK
    lateinit var extension: Extension

    @Test
    fun simpleTest() {
        val parser = WordParserWithExtension(initParser, extension)
        val parsedString = "a b'c'"
        every { initParser.parse(any()) } returns
                sequenceOf(JustWord("a"), DollarExpression("b"), FullQuotedText("'c'"))
        every { extension.extend(any()) } answers {
            firstArg<Sequence<Word>>().map { JustWord(it.text) }
        }

        assertEquals(
            listOf(JustWord("a"), JustWord("b"), JustWord("'c'")),
            parser.parse(parsedString).toList()
        )

        verifySequence {
            initParser.parse(parsedString)
            extension.extend(any())
        }
    }
}