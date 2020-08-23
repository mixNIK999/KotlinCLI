package me.spb.hse.nikolyukin.cli.parser.words

sealed class Word(val text: String) {
    override fun toString(): String {
        return "${this::class.simpleName}($text)"
    }

    override fun equals(other: Any?): Boolean {
        return other is Word && Pair(other::class, other.text) == Pair(this::class, text)
    }

    override fun hashCode(): Int {
        return Pair(this::class, text).hashCode()
    }

}

class WeakQuotedText(text: String) : Word(text)

class FullQuotedText(text: String) : Word(text)

class HomePath(text: String) : Word(text)

class DollarExpression(text: String) : Word(text)

class Pipe(text: String) : Word(text)

class JustWord(text: String) : Word(text)

class Spaces(text: String) : Word(text)