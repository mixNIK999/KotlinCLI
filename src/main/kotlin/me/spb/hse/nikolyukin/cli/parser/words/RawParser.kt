package me.spb.hse.nikolyukin.cli.parser.words

import com.github.h0tk3y.betterParse.combinators.or
import com.github.h0tk3y.betterParse.combinators.use
import com.github.h0tk3y.betterParse.combinators.zeroOrMore
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.Parser
import me.spb.hse.nikolyukin.cli.parser.words.CliRegex.dollarExpressionRegex
import me.spb.hse.nikolyukin.cli.parser.words.CliRegex.homePathRegex
import me.spb.hse.nikolyukin.cli.parser.words.CliRegex.quoteRegex
import me.spb.hse.nikolyukin.cli.parser.words.CliRegex.spaceRegex
import me.spb.hse.nikolyukin.cli.parser.words.CliRegex.wordRegex
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
class RawParser : Grammar<List<Word>>(), WordParser {
    // Tokens
    private val quote by regexToken(quoteRegex)
    private val pipe by literalToken("|")
    private val dollarExpression by regexToken(dollarExpressionRegex)
    private val homePath by regexToken(homePathRegex)
    private val spaces by regexToken(spaceRegex)
    private val word by regexToken(wordRegex)

    // Trivial parsers
    private val quoteParser by quote use {
        if (text.startsWith("'")) FullQuotedText(text) else WeakQuotedText(text)
    }
    private val dollarExpressionParser by dollarExpression use { DollarExpression(text.drop(1)) }
    private val homeParser by homePath use { HomePath(text) }
    private val pipeParser by pipe use { Pipe(text) }
    private val spacesParser by spaces use { Spaces(text) }
    private val justWordParser by word use { JustWord(text) }

    // Main parser
    override val rootParser: Parser<List<Word>> by zeroOrMore(
        quoteParser or dollarExpressionParser or homeParser or pipeParser or spacesParser or justWordParser
    )

    override fun parse(rawString: String): Sequence<Word> {
        logger.info("start parsing string: $rawString")
        return parseToEnd(rawString).asSequence()
    }
}