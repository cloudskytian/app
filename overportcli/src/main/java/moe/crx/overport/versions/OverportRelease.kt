package moe.crx.overport.versions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OverportRelease(
    @SerialName("version")
    var version: String,
    @SerialName("app_required")
    var appRequired: String? = null,
    @SerialName("is_experimental")
    var isExperimental: Boolean = false,
    @SerialName("download_url")
    var downloadUrl: String? = null,
    @SerialName("is_custom")
    var isCustom: Boolean = false,
)

@Serializable
data class ReleasesIndex(
    @SerialName("releases")
    var releases: List<OverportRelease>,
)