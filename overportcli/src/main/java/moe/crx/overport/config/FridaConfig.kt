package moe.crx.overport.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FridaAsset(
    @SerialName("ID")
    val id: Long = 0,
    val path: String = "",
    val type: String = "store"
)

@Serializable
data class FridaParameters(
    val assets: List<FridaAsset> = listOf(),
    val skus: List<String> = listOf()
)

@Serializable
data class FridaInteraction(
    val parameters: FridaParameters = FridaParameters(),
)

@Serializable
data class FridaConfig(
    val interaction: FridaInteraction = FridaInteraction(),
)