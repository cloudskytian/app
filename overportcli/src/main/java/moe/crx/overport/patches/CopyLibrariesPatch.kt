package moe.crx.overport.patches

import moe.crx.overport.patching.Patch
import java.io.File

val PATCH_COPY_LIBRARIES = Patch("patch_copy_libraries") {
    selectWorkspace {
        file.resolve("root/lib").listFiles()?.forEach { archDirectory ->
            val arch = getLibraries().resolve("lib/${archDirectory.name}")

            if (arch.exists()) {
                arch.listFiles().filter { it.name != "libOVRPlugin.so" }.forEach { library ->
                    library.copyTo(File(archDirectory, library.name), true)
                }
            }
        }
    }
}