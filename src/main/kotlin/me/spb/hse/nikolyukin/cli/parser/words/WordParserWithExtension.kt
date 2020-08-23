package me.spb.hse.nikolyukin.cli.parser.words

class WordParserWithExtension(
    private val initParser: WordParser,
    private val extensions: Extension
) : WordParser {

    override fun parse(rawString: String): Sequence<Word> = extensions.extend(initParser.parse(rawString))
}