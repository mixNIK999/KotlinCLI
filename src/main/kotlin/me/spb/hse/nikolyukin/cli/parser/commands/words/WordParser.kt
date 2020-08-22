package me.spb.hse.nikolyukin.cli.parser.commands.words

interface WordParser {
    fun parse(rawString: String): Sequence<Word>
}

