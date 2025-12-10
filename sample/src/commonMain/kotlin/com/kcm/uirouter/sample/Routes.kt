package com.kcm.uirouter.sample

import com.kcm.uirouter.Route

sealed class AppRoute(override val routeId: String) : Route {
    // Home tab
    data object Home : AppRoute("home")
    data class Details(val id: String, val title: String) : AppRoute("details") {
        override val parameters = mapOf("id" to id, "title" to title)
    }

    // Explore tab
    data object Explore : AppRoute("explore")
    data class ItemList(val category: String) : AppRoute("item_list") {
        override val parameters = mapOf("category" to category)
    }

    // Profile tab
    data object Profile : AppRoute("profile")
    data object EditProfile : AppRoute("edit_profile")

    // Modals
    data object Settings : AppRoute("settings")
    data class ImageViewer(val imageUrl: String) : AppRoute("image_viewer") {
        override val parameters = mapOf("imageUrl" to imageUrl)
    }
}
