package com.kcm.uirouter.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kcm.uirouter.rememberNavigationController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageViewerScreen(imageUrl: String) {
    val navigator = rememberNavigationController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Image Viewer") },
                navigationIcon = {
                    IconButton(onClick = { navigator.dismissModal() }) {
                        Icon(Icons.Default.Close, "Close")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color.Black
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Placeholder for image
                Card(
                    modifier = Modifier
                        .size(300.dp)
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Image Placeholder",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }

                Text(
                    "Full Screen Modal",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )

                Text(
                    "Image URL: $imageUrl",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navigator.dismissModal() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text("Close Viewer")
                }
            }
        }
    }
}
