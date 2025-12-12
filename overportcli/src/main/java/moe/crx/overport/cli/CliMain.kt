package moe.crx.overport.cli

import kotlinx.coroutines.runBlocking
import moe.crx.overport.cli.CommandStore.COMMANDS
import moe.crx.overport.commands.DisplayHelpCommand

abstract class CliCommand {
    abstract fun name(): String
    abstract fun description(): String
    abstract fun printHelp(): Boolean
    abstract suspend fun execute(args: List<String>): Boolean

    override fun toString() = name()
}

fun main(args: Array<String>) {
    val command = args.firstOrNull()?.let { COMMANDS[it] } ?: run {
        DisplayHelpCommand.printHelp()
        return
    }

    val successful = runBlocking {
        command.execute(args.drop(1))
    }
    if (!successful) {
        command.printHelp()
    }
}