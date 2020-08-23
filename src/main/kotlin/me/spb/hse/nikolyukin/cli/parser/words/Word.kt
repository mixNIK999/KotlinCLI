package me.spb.hse.nikolyukin.cli.parser.words

sealed class Word(val text: String) {
    override fun toString(): String {
        return "${this::class.simpleName}($text)"
    }
}

class WeakQuotedText(text: String) : Word(text)

class FullQuotedText(text: String) : Word(text)

class HomePath(text: String) : Word(text)

class DollarExpression(text: String) : Word(text)

class Pipe(text: String) : Word(text)

class JustWord(text: String) : Word(text)

class Spaces(text: String) : Word(text)