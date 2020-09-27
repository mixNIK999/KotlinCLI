package me.spb.hse.nikolyukin.cli

import me.spb.hse.nikolyukin.cli.parser.commands.WordToCommandParserAdapter
import me.spb.hse.nikolyukin.cli.parser.words.*
import me.spb.hse.nikolyukin.cli.shell.*
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.dsl.module

class ShellApplication : KoinComponent {
    val shell by inject<Shell>()

    fun run() = shell.run()
}

object ApplicationModules {
    val defaultModule = module {

        factory<Shell> {
            val env: Environment = get(Environment::class)
            val parser = WordToCommandParserAdapter(
                WordParserWithExtension(
                    RawParser(),
                    CompositeExtension(
                        listOf(
                            TildaExtension(env.homePath),
                            DollarExtension(env::get),
                            WordSplittingExtension()
                        )
                    )
                )
            )
            DefaultShell(env, parser, get())
        }
        single<ExecutionCommandFactory> { DefaultExecutionCommandFactory() }
        factory<Environment> { EnvironmentByMapImpl(mutableMapOf()) }
    }
}

fun main() {
    startKoin {
        modules(ApplicationModules.defaultModule)
    }
    ShellApplication().run()
}