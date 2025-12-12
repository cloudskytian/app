package moe.crx.overport.utils

import java.net.HttpURLConnection
import java.net.URI

object HttpUtil {
    fun download(url: String, timeout: Int = 5000): ByteArray {
        val uri = URI(url).toURL().openConnection() as HttpURLConnection
        uri.readTimeout = timeout
        uri.connectTimeout = 5000

        val bytes = uri.errorStream?.use {
            it.readBytes()
        } ?: uri.inputStream.use {
            it.readBytes()
        }

        uri.disconnect()
        return bytes
    }
}