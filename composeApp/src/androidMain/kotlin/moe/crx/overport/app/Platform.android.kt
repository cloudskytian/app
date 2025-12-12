package moe.crx.overport.app

import android.graphics.BitmapFactory
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

actual fun getPlatform(): Platform = AndroidPlatform("Android ${Build.VERSION.SDK_INT}")

actual fun ByteArray.decodeBitmap(): ImageBitmap {
    return BitmapFactory.decodeByteArray(this, 0, size).asImageBitmap()
}

@Composable
actual fun screenWidthDp(): Dp {
    return LocalConfiguration.current.screenWidthDp.dp
}