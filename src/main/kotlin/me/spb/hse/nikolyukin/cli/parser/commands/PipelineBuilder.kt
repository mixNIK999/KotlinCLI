package me.spb.hse.nikolyukin.cli.parser.commands

// not thread safe
// in case N pipe with no command between, N-1 pipe will be ignored
class PipelineBuilder {
    private val line = mutableListOf<Command>()
    private var currentName: Name? = null
    private val currentArgs: MutableList<Argument> = mutableListOf()

    fun addWord(word: String) {
        if (currentName == null) {
            currentName = Name(word)
        } else {
            currentArgs.add(Argument(word))
        }
    }

    fun addPipe() {
        if (currentName != null) {
            line.add(Command(currentName!!, currentArgs.toList()))
            currentName = null
            currentArgs.clear()
        }
    }

    fun toPipeline(): Pipeline {
        addPipe()
        return Pipeline(line.toList())
    }
}