package me.spb.hse.nikolyukin.cli.parser.commands

data class Pipeline(val commands: List<Command>)

data class Command(val name: Name, val args: List<Argument>)

data class Argument(val value: String)

data class Name(val text: String)
