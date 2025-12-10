package com.kcm.uirouter.sample

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kcm.uirouter.*

@Composable
fun SampleApp() {
    val tabs = listOf(
        Tab("home", AppRoute.Home),
        Tab("explore", AppRoute.Explore),
        Tab("profile", AppRoute.Profile)
    )

    val routes = buildRoutes {
        // Home tab routes
        route(AppRoute.Home) { HomeScreen() }
        route<AppRoute.Details>("details") { route ->
            val id = route.requireParameter<String>("id")
            val title = route.requireParameter<String>("title")
            DetailsScreen(id, title)
        }

        // Explore tab routes
        route(AppRoute.Explore) { ExploreScreen() }
        route<AppRoute.ItemList>("item_list") { route ->
            val category = route.requireParameter<String>("category")
            ItemListScreen(category)
        }

        // Profile tab routes
        route(AppRoute.Profile) { ProfileScreen() }
        route(AppRoute.EditProfile) { EditProfileScreen() }

        // Modal routes
        route(AppRoute.Settings) { SettingsScreen() }
        route<AppRoute.ImageViewer>("image_viewer") { route ->
            val imageUrl = route.requireParameter<String>("imageUrl")
            ImageViewerScreen(imageUrl)
        }
    }

    MaterialTheme {
        TabRouterHost(
            tabs = tabs,
            routes = routes,
            initialTabId = "home"
        ) { tabNavigator ->
            Column(modifier = Modifier.fillMaxSize()) {
                // Main content area
                Box(modifier = Modifier.weight(1f))

                // Bottom navigation bar
                BottomNavigationBar(
                    currentTabId = tabNavigator.currentTabId,
                    onTabSelected = { tabNavigator.switchTab(it) }
                )
            }
        }
    }
}
