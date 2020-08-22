package me.spb.hse.nikolyukin.cli.parser.commands

import me.spb.hse.nikolyukin.cli.parser.commands.words.JustWord
import me.spb.hse.nikolyukin.cli.parser.commands.words.WordParser

class WordToCommandParserAdapter(val wordParser: WordParser): CommandParser {
    override fun parse(rawString: String): Pipeline {
        TODO()
//        wordParser.parse(rawString).filter {  }
    }

}