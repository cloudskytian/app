package moe.crx.overport.utils

import moe.crx.overport.patching.PatcherContext

object NameFormatter {

    const val DEFAULT_FORMAT = "{filename}.output.{ext}"

    fun format(format: String, context: PatcherContext): String {
        return (format.takeIf { it.isNotBlank() } ?: DEFAULT_FORMAT)
            .replace("{package}", context.appPackage())
            .replace("{label}", context.appName())
            .replace("{code}", context.appVersionCode())
            .replace("{version}", context.appVersionName())
            .replace("{overport}", context.overportVersion())
            .replace("{filename}", context.inputFileName.substringBeforeLast('.'))
            .replace("{ext}", context.inputFileName.substringAfterLast('.'))
            .replace("{filenameext}", context.inputFileName)
    }
}