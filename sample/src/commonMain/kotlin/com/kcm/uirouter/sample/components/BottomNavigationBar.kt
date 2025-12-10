package com.kcm.uirouter.sample

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BottomNavigationBar(
    currentTabId: String,
    onTabSelected: (String) -> Unit
) {
    NavigationBar {
        TabItem(
            icon = Icons.Default.Home,
            label = "Home",
            selected = currentTabId == "home",
            onClick = { onTabSelected("home") }
        )

        TabItem(
            icon = Icons.Default.Search,
            label = "Explore",
            selected = currentTabId == "explore",
            onClick = { onTabSelected("explore") }
        )

        TabItem(
            icon = Icons.Default.Person,
            label = "Profile",
            selected = currentTabId == "profile",
            onClick = { onTabSelected("profile") }
        )
    }
}

@Composable
private fun RowScope.TabItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) },
        selected = selected,
        onClick = onClick
    )
}
