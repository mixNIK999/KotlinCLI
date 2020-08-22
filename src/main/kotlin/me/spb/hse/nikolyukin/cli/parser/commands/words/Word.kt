package me.spb.hse.nikolyukin.cli.parser.commands.words

sealed class Word(val text: String)

class WeakQuotedText(text: String) : Word(text)

class FullQuotedText(text: String) : Word(text)

class HomePath(text: String) : Word(text)

class DollarExpression(text: String) : Word(text)

class Pipe(text: String) : Word(text)

class JustWord(text: String) : Word(text)

class Spaces(text: String) : Word(text)