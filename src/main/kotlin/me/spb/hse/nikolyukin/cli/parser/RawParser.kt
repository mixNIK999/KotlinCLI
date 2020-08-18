package me.spb.hse.nikolyukin.cli.parser

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.Parser


class RawParser : Grammar<List<Word>>() {
    private val quote by regexToken(CliRegex.quoteRegex)
    private val pipe by literalToken("|")
    private val space by regexToken(CliRegex.spaceRegex)
    private val word by regexToken(CliRegex.wordRegex)

    override val rootParser: Parser<List<Word>> by zeroOrMore((word or space or pipe or quote) map { Word(it.text) })
}