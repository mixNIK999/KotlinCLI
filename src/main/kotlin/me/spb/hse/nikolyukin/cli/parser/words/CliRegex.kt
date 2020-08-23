package me.spb.hse.nikolyukin.cli.parser.words

object CliRegex {
    // regex for quoted string was taken from https://www.metaltoad.com/blog/regex-quoted-string-escapable-quotes
    const val quoteRegex = """((?<![\\])['"])((?:.(?!(?<![\\])\1))*.?)\1"""
    const val spaceRegex = "\\s+"

    const val wordRegex = """([^\s'"$~|]|\\"|\\'|\\$|\\~)+"""

    // matches only if tilda is the beginning of a word
    const val homePathRegex = "(?<=[\\s])~/"

    const val dollarExpressionRegex = "$$wordRegex"

}