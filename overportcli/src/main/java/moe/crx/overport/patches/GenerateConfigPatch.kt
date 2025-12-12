package moe.crx.overport.patches

import moe.crx.overport.config.updateDatabase
import moe.crx.overport.patching.Patch

val PATCH_GENERATE_CONFIG = Patch("patch_generate_config") {
    selectLibrary("libfrda.config.so") {
        readText().let { fridaJson ->
            selectConfig {
                updateDatabase(fridaJson)
            }
        }
    }
}