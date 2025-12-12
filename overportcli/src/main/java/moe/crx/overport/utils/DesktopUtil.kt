package moe.crx.overport.utils

import java.io.File

object DesktopUtil {
    fun defaultWorkspace(): File {
        return File(System.getProperty("user.home")).resolve("overport/cache")
    }
}