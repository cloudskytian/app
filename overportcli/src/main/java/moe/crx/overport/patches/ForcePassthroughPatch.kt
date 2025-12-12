package moe.crx.overport.patches

import moe.crx.overport.patching.Patch

val PATCH_FORCE_PASSTHROUGH = Patch("patch_force_passthrough", false) {
    selectConfig {
        forcePassthrough = 1
    }
}