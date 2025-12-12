package moe.crx.overport.patches

import moe.crx.overport.patching.Patch

val PATCH_META_XR_AUDIO = Patch("patch_meta_xr_audio") {
    selectLibrary("libMetaXRAudioWwise.so") {
        replaceHex(
            "57 D0 3B D5 40 EB FF F0 00 0C 2D 91 E8 16 40 F9 A8 83 1F F8 DC 7F 0B 94 00 01 00 B4 E3 03 00 AA 21 EB FF B0",
            "57 D0 3B D5 20 00 80 D2 1F 20 03 D5 E8 16 40 F9 A8 83 1F F8 1F 20 03 D5 00 01 00 B4 03 00 80 D2 21 EB FF B0"
        )
    }

    // TODO make a more universal patch?
    selectLibrary("libMetaXRAudioWwise.so") {
        replaceHex(
            "21 48 26 91 E0 03 14 AA 08 19 40 F9 00 01 3F D6 A0 07 00 B4 88 02 40 F9",
            "21 48 26 91 E0 03 14 AA E0 03 1F AA 1F 20 03 D5 A0 07 00 B4 88 02 40 F9"
        )
    }

    selectLibrary("libMetaXRAudioUnity.so") {
        replaceHex(
            "E0 03 14 AA 08 19 40 F9 00 01 3F D6 A0 07 00 B4 88 02 40 F9 E1 03 00 AA",
            "E0 03 14 AA 08 19 40 F9 E0 03 1F AA 44 00 00 14 88 02 40 F9 E1 03 00 AA"
        )
    }
}