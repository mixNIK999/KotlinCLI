package me.spb.hse.nikolyukin.cli.parser.words

interface WordParser {
    fun parse(rawString: String): Sequence<Word>
}

