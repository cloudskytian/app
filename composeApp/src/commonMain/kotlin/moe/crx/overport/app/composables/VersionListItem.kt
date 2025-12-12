package moe.crx.overport.app.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import moe.crx.overport.app.util.ModifierUtil.rounded
import moe.crx.overport.versions.OverportRelease
import moe.crx.overport.versions.VersionManager.Companion.isIncompatible
import org.jetbrains.compose.resources.stringResource
import overportapp.composeapp.generated.resources.*

@Composable
fun VersionListItem(release: OverportRelease, index: Int, isInstalled: Boolean, onSelected: () -> Unit) {
    ListItem(
        modifier = Modifier.rounded(8.dp).clickable(!isIncompatible(release)) {
            onSelected()
        },
        headlineContent = {
            FlowRow(
                itemVerticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(release.version)
                if (index == 0) {
                    Badge(containerColor = Color(0xFF1157CE)) {
                        Text(stringResource(Res.string.tag_latest))
                    }
                }
                if (release.isExperimental) {
                    Badge(containerColor = Color(0xFFB16300)) {
                        Text(stringResource(Res.string.tag_experimental))
                    }
                }
                if (release.isCustom) {
                    Badge(containerColor = Color(0xFFB60D6E)) {
                        Text(stringResource(Res.string.tag_custom))
                    }
                }
            }
        },
        supportingContent = {
            if (isInstalled) {
                if (!isIncompatible(release)) {
                    Text(stringResource(Res.string.status_installed))
                } else {
                    Text(stringResource(Res.string.status_installed_incompatible))
                }
            } else {
                if (!isIncompatible(release)) {
                    Text(stringResource(Res.string.status_not_installed))
                } else {
                    Text(stringResource(Res.string.status_not_installed_incompatible))
                }
            }
        },
        trailingContent = {
            if (!isInstalled && !isIncompatible(release)) {
                Icon(
                    Icons.Default.Download,
                    contentDescription = null,
                )
            }
        }
    )
}