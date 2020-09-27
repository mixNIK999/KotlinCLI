package me.spb.hse.nikolyukin.cli.parser.commands

interface CommandParser {
    fun parse(rawString: String): Pipeline
}