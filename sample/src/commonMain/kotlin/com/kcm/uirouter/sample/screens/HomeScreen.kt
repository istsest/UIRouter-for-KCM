package com.kcm.uirouter.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kcm.uirouter.rememberNavigationController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val navigator = rememberNavigationController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    IconButton(onClick = {
                        navigator.presentSheet(AppRoute.Settings, dismissible = true)
                    }) {
                        Icon(Icons.Default.Settings, "Settings")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Welcome to UIRouter Sample",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                "Tap items below to test navigation:",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Demo items
            val items = listOf(
                "Item 1" to "First item details",
                "Item 2" to "Second item details",
                "Item 3" to "Third item details",
                "Item 4" to "Fourth item details",
                "Item 5" to "Fifth item details"
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { (title, description) ->
                    ItemCard(
                        title = title,
                        description = description,
                        onClick = {
                            navigator.push(
                                AppRoute.Details(
                                    id = title.replace(" ", "_").lowercase(),
                                    title = title
                                )
                            )
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            navigator.presentFullScreen(
                                AppRoute.ImageViewer("sample_image_url"),
                                dismissible = true
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Open Image Viewer (Full Screen)")
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Navigate",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
