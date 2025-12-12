package moe.crx.overport.patches

import com.reandroid.json.JSONArray
import com.reandroid.json.JSONObject
import moe.crx.overport.patching.Patch
import moe.crx.overport.utils.elem
import moe.crx.overport.utils.take
import moe.crx.overport.utils.takeEach

val PATCH_REMOVE_LOCALIZED_NAMES = Patch("patch_remove_localized_names") {
    selectResourcesJson {
        take<JSONArray>("packages") {
            takeEach<JSONObject> {
                take<JSONArray>("specs") {
                    takeEach<JSONObject> {
                        take<JSONArray>("types") {
                            takeEach<JSONObject> {
                                val configLanguage = elem<JSONObject>("config").elem<String>("language")

                                take<JSONArray>("entries") {
                                    takeEach<JSONObject> {
                                        this.takeIf {
                                            elem<String>("entry_name") != "app_name" || configLanguage == null
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}