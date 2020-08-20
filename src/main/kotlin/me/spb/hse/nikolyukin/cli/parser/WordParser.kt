package me.spb.hse.nikolyukin.cli.parser

interface WordParser {
    fun parse(rawString: String): Sequence<Word>
}

