package moe.crx.overport.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import moe.crx.overport.utils.HttpUtil

@Serializable
data class ApplicationMetaImage(
    @SerialName("file_name")
    var fileName: String,
    @SerialName("id")
    var id: String,
    @SerialName("image_type")
    var imageType: String,
    @SerialName("uri")
    var uri: String,
)

@Serializable
data class ApplicationMetaInfo(
    @SerialName("metaID")
    var metaId: String,
    @SerialName("displayName")
    var displayName: String,
    @SerialName("images")
    var images: List<ApplicationMetaImage>,
)

private val json = Json { ignoreUnknownKeys = true }

fun getApplicationInfo(pkg: String): ApplicationMetaInfo? {
    val url = "https://ovrp.crx.moe/images/by_package?package=$pkg"
    return runCatching {
        val string = String(HttpUtil.download(url, timeout = 20000))
        json.decodeFromString<ApplicationMetaInfo>(string)
    }.getOrNull()
}