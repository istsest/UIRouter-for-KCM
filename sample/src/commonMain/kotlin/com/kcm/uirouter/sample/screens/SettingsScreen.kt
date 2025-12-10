package com.kcm.uirouter.sample

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kcm.uirouter.rememberNavigationController

@Composable
fun SettingsScreen() {
    val navigator = rememberNavigationController()
    var notifications by remember { mutableStateOf(true) }
    var darkMode by remember { mutableStateOf(false) }
    var autoPlay by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Settings",
                style = MaterialTheme.typography.headlineMedium
            )

            Divider()

            // Notifications
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Enable Notifications")
                Switch(
                    checked = notifications,
                    onCheckedChange = { notifications = it }
                )
            }

            // Dark Mode
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Dark Mode")
                Switch(
                    checked = darkMode,
                    onCheckedChange = { darkMode = it }
                )
            }

            // Auto Play
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Auto Play Videos")
                Switch(
                    checked = autoPlay,
                    onCheckedChange = { autoPlay = it }
                )
            }

            Divider()

            Text(
                "This is a Sheet Modal",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                "You can dismiss it by tapping outside or using the button below.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Button(
                onClick = { navigator.dismissModal() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Close Settings")
            }
        }
    }
}
