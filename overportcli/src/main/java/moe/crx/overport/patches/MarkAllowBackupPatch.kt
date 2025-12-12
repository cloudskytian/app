package moe.crx.overport.patches

import com.reandroid.json.JSONObject
import moe.crx.overport.patching.Patch
import moe.crx.overport.utils.*

fun createAllowBackup(): JSONObject {
    return JSONObject().put("name", "allowBackup").put("id", 16843392)
        .put("uri", "http://schemas.android.com/apk/res/android").put("prefix", "android").put("value_type", "BOOLEAN")
        .put("data", true)
}

val PATCH_MARK_ALLOW_BACKUP = Patch("patch_mark_allow_backup") {
    selectManifestJson {
        takeNodesEach({ named("manifest") }) {
            takeNodesEach({ named("application") }) {
                takeAttributes {
                    if (elemEach<JSONObject> { named("allowBackup") }.isEmpty()) {
                        this?.put(createAllowBackup())
                    }

                    this
                }
            }
        }
    }
}