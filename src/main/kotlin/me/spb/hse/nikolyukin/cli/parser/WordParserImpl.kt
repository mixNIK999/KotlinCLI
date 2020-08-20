package me.spb.hse.nikolyukin.cli.parser

import java.nio.file.Path

class WordParserImpl(getVarValue: (String) -> String?) : WordParser {

    val rawParser = RawParser()
    val extensions = CompositeExtension(
        listOf(
            TildaExtension(Path.of(getVarValue("HOME") ?: "")),
            DollarExtension(getVarValue),
            WordSplittingExtension()
        )
    )

    override fun parse(rawString: String): Sequence<Word> {
        TODO("Not yet implemented")
    }
}