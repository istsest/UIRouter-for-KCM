package com.kcm.uirouter.sample

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kcm.uirouter.rememberNavigationController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(id: String, title: String) {
    val navigator = rememberNavigationController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Item Details",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text("ID: $id")
                    Text("Title: $title")
                }
            }

            Text(
                "Navigation Stack Test",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                "This screen demonstrates push/pop navigation. " +
                        "You can navigate back using the back button or system back gesture.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { navigator.pop() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Pop")
                }

                Button(
                    onClick = { navigator.popToRoot() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Pop to Root")
                }
            }
        }
    }
}
