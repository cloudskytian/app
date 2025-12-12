package moe.crx.overport.patches

import moe.crx.overport.patching.Patch

val PATCH_REMOVE_UNREAL_FORCE_QUIT = Patch("patch_remove_unreal_force_quit", false) {
    selectSmali(
        "com/epicgames/ue4/GameActivity",
        "com/epicgames/unreal/GameActivity",
    ) {
        replace(
            "(\\.method public AndroidThunkJava_ForceQuit\\(\\)V.*?)(invoke-static \\{\\w\\d+?\\}, Ljava\\/lang\\/System;->exit\\(I\\)V)(.*?\\.end method)",
            "$1$3"
        )
    }
}