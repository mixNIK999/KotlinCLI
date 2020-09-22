package me.spb.hse.nikolyukin.cli.parser.commands

import me.spb.hse.nikolyukin.cli.parser.words.EqualitySign
import me.spb.hse.nikolyukin.cli.parser.words.JustWord
import me.spb.hse.nikolyukin.cli.parser.words.Pipe
import me.spb.hse.nikolyukin.cli.parser.words.WordParser

class WordToCommandParserAdapter(private val wordParser: WordParser) : CommandParser {
    override fun parse(rawString: String): Pipeline {
        val builder = PipelineBuilder()
        wordParser.parse(rawString)
            .forEach { word ->
                when (word) {
                    is JustWord, is EqualitySign -> builder.addWord(word.text)
                    is Pipe -> builder.addPipe()
                    else -> Unit
                }
            }
        return builder.toPipeline()
    }

}