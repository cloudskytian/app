package moe.crx.overport.patches

import moe.crx.overport.patching.Patch

val PATCH_DISABLE_SPACE_WARP = Patch("patch_disable_space_warp", false) {
    selectConfig {
        disableSpaceWarp = 1
    }
}