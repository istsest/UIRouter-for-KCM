package com.kcm.uirouter.sample

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kcm.uirouter.rememberNavigationController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen() {
    val navigator = rememberNavigationController()

    val categories = listOf(
        "Technology", "Science", "Arts", "Sports",
        "Music", "Travel", "Food", "Fashion"
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Explore") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                "Browse Categories",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    CategoryCard(
                        category = category,
                        onClick = {
                            navigator.push(AppRoute.ItemList(category))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryCard(category: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
