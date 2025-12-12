package moe.crx.overport.patches

import moe.crx.overport.patching.Patch

val PATCH_OCULUS_UNREAL = Patch("patch_oculus_unreal") {
    selectSmali(
        "com/epicgames/ue4/GameActivity",
        "com/epicgames/unreal/GameActivity",
    ) {
        replace(
            "sget-object (\\w+), Landroid/os/Build;->MANUFACTURER:Ljava/lang/String;", "const-string $1, \"Oculus\""
        )
        replace(
            "sget-object (\\w+), Landroid/os/Build;->MODEL:Ljava/lang/String;", "const-string $1, \"Quest 2\""
        )
    }
}