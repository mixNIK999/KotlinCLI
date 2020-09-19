package me.spb.hse.nikolyukin.cli.shell

interface Environment {
    operator fun get(name: String): String?
    operator fun set(name: String, value: String)
}