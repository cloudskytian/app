package moe.crx.overport.patches

import com.reandroid.json.JSONObject
import moe.crx.overport.patching.Patch
import moe.crx.overport.utils.*

fun createDebuggable(): JSONObject {
    return JSONObject().put("name", "debuggable").put("id", 16842767)
        .put("uri", "http://schemas.android.com/apk/res/android").put("prefix", "android").put("value_type", "BOOLEAN")
        .put("data", true)
}

val PATCH_MARK_AS_DEBUGGABLE = Patch("patch_mark_as_debuggable") {
    selectManifestJson {
        takeNodesEach({ named("manifest") }) {
            takeNodesEach({ named("application") }) {
                takeAttributes {
                    if (elemEach<JSONObject> { named("debuggable") }.isEmpty()) {
                        this?.put(createDebuggable())
                    }

                    this
                }
            }
        }
    }
}