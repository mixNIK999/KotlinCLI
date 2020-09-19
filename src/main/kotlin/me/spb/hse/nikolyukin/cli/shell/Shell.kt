package me.spb.hse.nikolyukin.cli.shell

interface Shell {
    // start interactive shell
    suspend fun run()
}