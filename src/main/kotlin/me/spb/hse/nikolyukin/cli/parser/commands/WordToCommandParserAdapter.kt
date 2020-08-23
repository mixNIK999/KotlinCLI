package me.spb.hse.nikolyukin.cli.parser.commands

import me.spb.hse.nikolyukin.cli.parser.words.WordParser
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
class WordToCommandParserAdapter(val wordParser: WordParser): CommandParser {
    override fun parse(rawString: String): Pipeline {
        TODO()
//        wordParser.parse(rawString)
//            .filter { word ->
//                if (word is JustWord || word is Pipe) true
//                else {
//                    if (word !is Spaces) logger.error { "Unexpected word type $word" }
//                    false
//                }
//            }
//            .fol
    }

}