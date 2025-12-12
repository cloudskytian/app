package moe.crx.overport.commands

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import moe.crx.overport.cli.CliCommand
import moe.crx.overport.utils.DesktopUtil.defaultWorkspace
import moe.crx.overport.utils.PrintUtil.drawSpinner
import moe.crx.overport.utils.PrintUtil.updateLine
import moe.crx.overport.versions.OverportRelease
import moe.crx.overport.versions.VersionManager
import moe.crx.overport.versions.VersionManager.Companion.isIncompatible
import java.io.File

object VersionManagerCommand : CliCommand() {

    val scope = CoroutineScope(Dispatchers.IO)

    override fun name() = "install"

    override fun description() = "Manage overport instances."

    override fun printHelp(): Boolean {
        println("usage: overport install [--workspace=<path>] [--version=<value>]")
        println()
        println("--workspace=<path>")
        println("Specifies workspace directory path.")
        println("If not specified, uses default one in user's home directory.")
        println()
        println("--version=<value>")
        println("Specifies which overport version to install or remove.")
        println("Value also could be \"experimental\" or \"latest\".")
        println("If not specified, prints all available or installed versions.")
        println()
        println("--remove")
        println("Specifies if the version should be removed.")
        println("If not specified, manager installs this version.")
        return true
    }

    private data class ParsedArguments(
        var workspace: String? = null,
        var overportVersion: String? = null,
        var remove: Boolean = false,
    )

    private fun scanArguments(args: List<String>): ParsedArguments {
        val parsed = ParsedArguments()

        val associated = args.associate {
            it.substringBefore('=') to it.substringAfter('=', "")
        }

        associated.forEach { (key, value) ->
            when (key) {
                "--workspace" -> parsed.workspace = value
                "--version" -> parsed.overportVersion = value
                "--remove" -> parsed.remove = true
            }
        }

        return parsed
    }

    fun printVersionList(listed: List<OverportRelease>, installed: List<OverportRelease>) {
        listed.forEachIndexed { index, rel ->
            val isInstalled = installed.firstOrNull { it.version == rel.version } != null

            print("[")
            if (!isInstalled) {
                print(" ")
            } else {
                print("x")
            }
            print("] ")
            print(rel.version)

            if (index == 0) {
                print(", latest")
            }
            if (rel.isExperimental) {
                print(", experimental")
            }
            if (rel.isCustom) {
                print(", custom")
            }

            if (isInstalled) {
                print(" [installed]")
            }
            if (isIncompatible(rel)) {
                print(" [incompatible]")
            }

            println()
        }
    }

    suspend fun printVersionsInfo(workspace: File) {
        val manager = VersionManager(workspace)

        val waiting = updateLine {
            drawSpinner()
            print("Waiting for versions index...")
        }
        val available = scope.async { manager.available() }.await()
        val installed = manager.installed()

        waiting.cancelAndJoin()

        if (available.isEmpty()) {
            println("overport release index is not available. Check your internet connection.")

            if (installed.isEmpty()) {
                println("You have no versions installed so you can't use patching yet.")
            } else {
                println("You have some versions installed that are available offline:")
                printVersionList(installed, installed)
            }

            return
        }

        println("Available versions:")
        printVersionList(available, installed)

        val noMoreAvailable = installed.filter { rel -> available.firstOrNull { rel.version == it.version } == null }
        printVersionList(noMoreAvailable, installed)
    }

    override suspend fun execute(args: List<String>): Boolean {
        val parsed = scanArguments(args)

        val workspace = parsed.workspace?.let { File(it) }
            ?: defaultWorkspace()

        val version = parsed.overportVersion

        if (version == null) {
            printVersionsInfo(workspace)
        } else {
            if (parsed.remove) {
                VersionManager(workspace).uninstall(version)
            } else {
                VersionManager(workspace).checkout(version)
            }
        }

        return true
    }
}