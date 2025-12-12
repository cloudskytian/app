package moe.crx.overport.patches

import moe.crx.overport.patching.Patch
import moe.crx.overport.utils.named
import moe.crx.overport.utils.take
import moe.crx.overport.utils.takeAttributesEach
import moe.crx.overport.utils.takeNodesEach

val PATCH_FIX_MIN_ANDROID_SDK = Patch("patch_fix_min_android_sdk") {
    selectManifestJson {
        takeNodesEach({ named("manifest") }) {
            takeNodesEach({ named("uses-sdk") }) {
                takeAttributesEach({ named("minSdkVersion") }) {
                    take<Int>("data") { 29 }
                }
            }
        }
    }

    selectSmali {
        replace(
            "invoke-virtual \\{(\\w+), (\\w+)\\}, Landroid/view/Window;->setDecorFitsSystemWindows\\(Z\\)V", ""
        )
    }

    selectSmali("androidx/core/view/WindowCompat\$Api30Impl") {
        replace(
            "invoke-virtual \\{(\\w+), (\\w+)\\}, Landroid/view/Window;->setDecorFitsSystemWindows\\(Z\\)V", ""
        )
    }

    selectSmali("androidx/core/view/WindowInsetsCompat\$TypeImpl30") {
        replace(
            "(toPlatformType\\(I\\)I.*?)\\.locals (\\w+)", "$0\nconst v0, 0x0\nreturn v0"
        )
    }

    selectSmali("androidx/core/view/WindowInsetsCompat\$Impl30") {
        replace(
            "(getInsets\\(I\\)Landroidx/core/graphics/Insets;.*?)\\.locals (\\w+)",
            "$0\nsget-object p0, Landroidx/core/graphics/Insets;->NONE:Landroidx/core/graphics/Insets;\nreturn-object p0"
        )
        replace("(isVisible\\(I\\)Z.*?)\\.locals (\\w+)", "$0\nconst/4 p0, 0\nreturn p0")
    }

    selectSmali("com/epicgames/unreal/GameActivity") {
        replace(
            "(if-lt \\w+?, \\w+?, :\\S+\\s*?)?const-string \\w+?, \\\"vibrator_manager\\\".*?iput-object \\w+?, \\w+?, Lcom/epicgames/unreal/GameActivity;->\\w+?:Landroid/os/Vibrator;",
            ""
        )
    }

    selectSmali("com/unity3d/player/PlatformSupport") {
        replace("(\\.field static final RED_VELVET_CAKE_SUPPORT:Z)( = true)?( = false)?", "$1 = false")
        replace("(\\.field static final SNOW_CONE_SUPPORT:Z)( = true)?( = false)?", "$1 = false")
        replace("sput-boolean v1, Lcom\\/unity3d\\/player\\/PlatformSupport;->RED_VELVET_CAKE_SUPPORT:Z", "")
        replace("sput-boolean v1, Lcom\\/unity3d\\/player\\/PlatformSupport;->SNOW_CONE_SUPPORT:Z", "")
    }
}