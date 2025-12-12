package moe.crx.overport.app.util

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp

object ModifierUtil {
    @Composable
    fun Modifier.rounded(radius: Dp): Modifier {
        return clip(RoundedCornerShape(radius))
    }
}