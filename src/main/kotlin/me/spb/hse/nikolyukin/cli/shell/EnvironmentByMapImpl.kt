package me.spb.hse.nikolyukin.cli.shell

class EnvironmentByMapImpl(private val map: MutableMap<String, String>) : Environment {
    override fun get(name: String): String? {
        return map[name]
    }

    override fun set(name: String, value: String) {
        map[name] = value
    }
}