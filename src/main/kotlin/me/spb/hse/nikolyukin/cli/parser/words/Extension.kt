package me.spb.hse.nikolyukin.cli.parser.words

import java.io.File
import java.nio.file.Path

interface Extension {
    fun extend(words: Sequence<Word>): Sequence<Word>
}

class CompositeExtension(private val extensionSequence: List<Extension>) : Extension {
    override fun extend(words: Sequence<Word>): Sequence<Word> =
        extensionSequence.fold(words) { currentWords, currentExtension ->
            currentExtension.extend(currentWords)
        }
}

class DollarExtension(private val mapFun: (varName: String) -> String?) : Extension {
    override fun extend(words: Sequence<Word>): Sequence<Word> =
        words.mapNotNull { word ->
            when (word) {
                is DollarExpression -> getValue(word.text)?.let { JustWord(it) }
                is WeakQuotedText -> WeakQuotedText(
                    word.text.replace(CliRegex.dollarExpressionRegex.toRegex()) {
                        getValue(it.value) ?: ""
                    }
                )
                else -> word
            }
        }

    private fun getValue(dollarExtension: String): String? = mapFun(dollarExtension.drop(1))
}

class TildaExtension(private val homePath: Path) : Extension {
    override fun extend(words: Sequence<Word>): Sequence<Word> =
        words.map { word ->
            when (word) {
                is HomePath -> JustWord(homePath.toString() + File.separator)
                else -> word
            }
        }
}

// converts sequence to format only with JustWord, Pipe and Spaces
// example: "[JustWord, Spaces, JustWord, Pipe, JustWord]"
class WordSplittingExtension : Extension {
    override fun extend(words: Sequence<Word>): Sequence<Word> {
        val currentStringBuilder = StringBuilder()
        return words.flatMap { word ->
            when (word) {
                is Pipe -> sequenceOf(word)
                is Spaces -> sequenceOf(JustWord(currentStringBuilder.toString()), word)
                else -> {
                    currentStringBuilder.append(word)
                    emptySequence()
                }
            }
        }
    }
}