package me.spb.hse.nikolyukin.cli.parser

import org.intellij.lang.annotations.Language

object CliRegex {
    // regex for quoted string was taken from https://www.metaltoad.com/blog/regex-quoted-string-escapable-quotes
    val quoteRegex = """((?<![\\])['"])((?:.(?!(?<![\\])\1))*.?)\1"""
    val spaceRegex = "\\s+"

    // TODO add escape character
    val wordRegex = """[^\s|"']+"""
}