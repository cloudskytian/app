package moe.crx.overport.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp

interface Platform {
    val name: String
}

class JVMPlatform(override val name: String) : Platform

class AndroidPlatform(override val name: String) : Platform

expect fun getPlatform(): Platform

expect fun ByteArray.decodeBitmap(): ImageBitmap

@Composable
expect fun screenWidthDp(): Dp