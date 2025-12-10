package com.kcm.uirouter.sample

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kcm.uirouter.rememberNavigationController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val navigator = rememberNavigationController()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Profile") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "John Doe",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        "john.doe@example.com",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Button(
                onClick = { navigator.push(AppRoute.EditProfile) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit Profile")
            }

            OutlinedButton(
                onClick = { navigator.presentSheet(AppRoute.Settings) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Settings (Sheet Modal)")
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                "This is the Profile tab. It maintains its own navigation stack " +
                        "independent of other tabs.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
