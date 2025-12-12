package moe.crx.overport.patches

import moe.crx.overport.patching.Patch

val PATCH_OCULUS_UNITY = Patch("patch_oculus_unity") {
    selectSmali("com/unity/oculus/OculusUnity") {
        replace(
            "(getIsOnOculusHardware\\(\\)Z.*?)return (\\w+)", "$1\nconst/4 $2, 0x1\nreturn $2"
        )
    }
}