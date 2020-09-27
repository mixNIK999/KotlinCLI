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
                is DollarExpression -> mapFun(word.text)?.let { JustWord(it) }
                is WeakQuotedText -> WeakQuotedText(
                    word.text.replace(CliRegex.dollarExpressionRegex.toRegex()) {
                        mapFun(it.value.drop(1)) ?: ""
                    }
                )
                else -> word
            }
        }
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

// converts sequence to format with only JustWord, EqualitySign, Pipe and Spaces
// example: "[JustWord, Spaces, JustWord, Pipe, EqualitySign, JustWord, JustWord]"
class WordSplittingExtension : Extension {
    override fun extend(words: Sequence<Word>): Sequence<Word> {
        val stringBuilder = StringBuilder()
        return sequence {
            yieldAll(words.flatMap { word ->
                when (word) {
                    is EqualitySign, is Pipe, is Spaces -> sequence<Word> {
                        flushString(stringBuilder)
                        yield(word)
                    }
                    is WeakQuotedText -> {
                        stringBuilder.append(word.text.removeSurrounding("\""))
                        emptySequence()
                    }
                    is FullQuotedText -> {
                        stringBuilder.append(word.text.removeSurrounding("'"))
                        emptySequence()
                    }
                    else -> {
                        stringBuilder.append(word.text)
                        emptySequence()
                    }
                }
            })
            flushString(stringBuilder)
        }
    }

    companion object {
        private suspend fun SequenceScope<Word>.flushString(buffer: StringBuilder) {
            if (buffer.isNotEmpty()) this.yield(JustWord(buffer.toString()))
            buffer.clear()
        }
    }
}