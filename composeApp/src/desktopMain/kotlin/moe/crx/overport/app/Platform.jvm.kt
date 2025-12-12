package moe.crx.overport.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.skia.Image

actual fun getPlatform(): Platform = JVMPlatform("Java ${System.getProperty("java.version")}")

actual fun ByteArray.decodeBitmap(): ImageBitmap {
    return Image.makeFromEncoded(this).toComposeImageBitmap()
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun screenWidthDp(): Dp {
    return (LocalWindowInfo.current.containerSize.width / LocalDensity.current.density).dp
}