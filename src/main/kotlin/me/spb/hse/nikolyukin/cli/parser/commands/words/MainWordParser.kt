package me.spb.hse.nikolyukin.cli.parser.commands.words

class MainWordParser(
    val initParser : WordParser,
    val extensions: Extension
) : WordParser {

    override fun parse(rawString: String): Sequence<Word> = extensions.extend(initParser.parse(rawString))
}