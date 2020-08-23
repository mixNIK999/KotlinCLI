package me.spb.hse.nikolyukin.cli.parser.commands

import me.spb.hse.nikolyukin.cli.parser.words.JustWord
import me.spb.hse.nikolyukin.cli.parser.words.Pipe
import me.spb.hse.nikolyukin.cli.parser.words.Spaces
import me.spb.hse.nikolyukin.cli.parser.words.WordParser
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class WordToCommandParserAdapter(private val wordParser: WordParser) : CommandParser {
    override fun parse(rawString: String): Pipeline {
        val builder = PipelineBuilder()
        wordParser.parse(rawString)
            .forEach { word ->
                when (word) {
                    is JustWord -> builder.addWord(word.text)
                    is Pipe -> builder.addPipe()
                    is Spaces -> {
                    }
                    else -> logger.error { "Ignore unexpected word type $word" }
                }
            }
        return builder.toPipeline()
    }

}