package me.spb.hse.nikolyukin.cli.parser.commands

import me.spb.hse.nikolyukin.cli.parser.words.*
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class WordToCommandParserAdapter(private val wordParser: WordParser) : CommandParser {
    override fun parse(rawString: String): Pipeline {
        val builder = PipelineBuilder()
        wordParser.parse(rawString)
            .forEach { word ->
                when (word) {
                    is JustWord, is EqualitySign -> builder.addWord(word.text)
                    is Pipe -> builder.addPipe()
                    is Spaces -> Unit
                    else -> logger.error { "Ignore unexpected word type $word" }
                }
            }
        return builder.toPipeline()
    }

}