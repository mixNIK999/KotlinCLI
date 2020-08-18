package me.spb.hse.nikolyukin.cli.parser

import com.github.h0tk3y.betterParse.combinators.or
import com.github.h0tk3y.betterParse.combinators.separatedTerms
import com.github.h0tk3y.betterParse.combinators.use
import com.github.h0tk3y.betterParse.combinators.zeroOrMore
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.lexer.DefaultTokenizer
import com.github.h0tk3y.betterParse.lexer.LiteralToken
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.toParsedOrThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BetterParse {
    @Test
    fun tokenParse() {
        val str = "a b c"
        val aToken = literalToken("a")
        val bToken = literalToken("b")
        val cToken = literalToken("c")
        val spaceToken = literalToken(" ")
        val parser = zeroOrMore(aToken or bToken or cToken or spaceToken use { text })
        val tokenMatches = DefaultTokenizer(listOf(aToken, bToken, cToken, spaceToken)).tokenize(str)
        assertEquals(listOf("a", " ", "b", " ", "c"), parser.tryParse(tokenMatches, 0).toParsedOrThrow().value.toList())
    }

    interface Item
    data class Number(val value: Int) : Item
    data class Variable(val name: String) : Item

    class ItemsParser : Grammar<List<Item>>() {
        val num by regexToken("\\d+")
        val word by regexToken("[A-Za-z]+")
        val comma by regexToken(",\\s+")

        val numParser by num use { Number(text.toInt()) }
        val varParser by word use { Variable(text) }

        override val rootParser by separatedTerms(numParser or varParser, comma)
    }

    @Test
    fun testGrammar() {
        val result: List<Item> = ItemsParser().parseToEnd("one, 2, three, 4, five")
        assertEquals(listOf(Variable("one"), Number(2), Variable("three"), Number(4), Variable("five")), result)
    }

    class ItemsParserAsString : Grammar<List<String>>() {
        val num by regexToken("\\d+")
        val word by regexToken("[A-Za-z]+")
        val space by regexToken("\\s+")

        override val rootParser by zeroOrMore(num or word or space use { text })
    }

    @Test
    fun testGrammarWithOnlyString() {
        val result = ItemsParserAsString().parseToEnd("one 2 three 4 five")
        assertEquals(listOf("one", " ", "2", " ", "three", " ", "4", " ", "five"), result)
    }
}