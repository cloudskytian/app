package moe.crx.overport.utils

import com.reandroid.json.JSONArray
import com.reandroid.json.JSONObject

fun JSONObject?.named(name: String?): Boolean {
    return elem<String>("name") == name
}

fun JSONObject?.takeNodes(block: JSONArray?.() -> JSONArray? = { this }): JSONObject? {
    return take("nodes", block)
}

fun JSONObject?.takeAttributes(block: JSONArray?.() -> JSONArray? = { this }): JSONObject? {
    return take("attributes", block)
}

fun JSONObject?.takeNodesEach(
    condition: JSONObject?.() -> Boolean = { true }, block: JSONObject?.() -> JSONObject? = { this }
): JSONObject? {
    return takeNodes {
        takeEach(condition, block)
    }
}

fun JSONObject?.takeAttributesEach(
    condition: JSONObject?.() -> Boolean = { true }, block: JSONObject?.() -> JSONObject? = { this }
): JSONObject? {
    return takeAttributes {
        takeEach(condition, block)
    }
}

fun JSONObject?.nameAttribute(): String? {
    return elem<JSONArray>("attributes").elemEach<JSONObject> { named("name") }.firstOrNull().elem<String>("data")
}
