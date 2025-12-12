package moe.crx.overport.app.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import moe.crx.overport.app.AndroidPlatform
import moe.crx.overport.app.getPlatform
import moe.crx.overport.app.screenWidthDp

data class BottomNavItem(
    val name: String,
    val selectedIcon: ImageVector,
    val icon: ImageVector,
    val content: @Composable (() -> Unit),
)

@Composable
fun DynamicScaffold(
    snackbarHost: @Composable (() -> Unit),
    topBar: @Composable (() -> Unit),
    navigationItems: List<BottomNavItem>,
    itemsEnabled: Boolean,
) {
    var selectedItem by remember { mutableStateOf(navigationItems.firstOrNull()) }
    val isTablet = screenWidthDp() > 600.dp

    Scaffold(
        snackbarHost = snackbarHost,
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (getPlatform() !is AndroidPlatform) Modifier else {
                    Modifier.clip(RoundedCornerShape(32.dp))
                }
            ),
        topBar = topBar,
        bottomBar = {
            if (!isTablet && navigationItems.size > 1) {
                NavigationBar {
                    navigationItems.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    if (selectedItem == item) item.selectedIcon else item.icon,
                                    contentDescription = item.name
                                )
                            },
                            label = { Text(item.name) },
                            selected = selectedItem == item,
                            onClick = { selectedItem = item },
                            enabled = itemsEnabled
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (isTablet && navigationItems.size > 1) {
                NavigationRail {
                    navigationItems.forEach { item ->
                        NavigationRailItem(
                            icon = {
                                Icon(
                                    if (selectedItem == item) item.selectedIcon else item.icon,
                                    contentDescription = item.name
                                )
                            },
                            label = { Text(item.name) },
                            selected = selectedItem == item,
                            onClick = { selectedItem = item },
                            enabled = itemsEnabled
                        )
                    }
                }
            }
            navigationItems.forEach { item ->
                FadeVisibility(selectedItem == item) {
                    item.content()
                }
            }
        }
    }
}