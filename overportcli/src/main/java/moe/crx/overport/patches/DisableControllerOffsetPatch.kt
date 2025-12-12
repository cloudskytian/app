package moe.crx.overport.patches

import moe.crx.overport.patching.Patch

val PATCH_DISABLE_CONTROLLER_OFFSET = Patch("patch_disable_controller_offset", false) {
    selectConfig {
        disableControllerOffset = 1
    }
}