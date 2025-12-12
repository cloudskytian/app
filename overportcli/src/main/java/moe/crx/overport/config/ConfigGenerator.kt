package moe.crx.overport.config

import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

fun OverportConfig.updateDatabase(fridaString: String) {
    val config = runCatching {
        json.decodeFromString<FridaConfig>(fridaString)
    }.getOrNull() ?: return

    val products = config.interaction.parameters.skus
    val assets = config.interaction.parameters.assets.map { OverportConfigAsset(it.id, it.path, it.type) }

    val application = OverportConfigApplication("__current__", products, assets)
    database = listOf(application)
}
