package com.kcm.uirouter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * NavigationController provides a convenient API for navigation operations
 * This is a higher-level abstraction over Navigator
 */
class NavigationController internal constructor(
    private val navigator: Navigator
) {
    /**
     * Push a new route onto the navigation stack
     */
    fun push(route: Route) {
        navigator.navigate(route)
    }

    /**
     * Pop the current route and go back
     */
    fun pop() {
        navigator.navigateBack()
    }

    /**
     * Pop to a specific route in the stack
     */
    fun popTo(route: Route) {
        navigator.navigateBackTo(route)
    }

    /**
     * Pop to the root of the navigation stack
     */
    fun popToRoot() {
        navigator.navigateBackToRoot()
    }

    /**
     * Replace the current route with a new route
     */
    fun replace(route: Route) {
        navigator.replace(route)
    }

    /**
     * Replace all routes with a new root route
     */
    fun replaceAll(route: Route) {
        navigator.replaceAll(route)
    }

    /**
     * Present a sheet modal
     */
    fun presentSheet(route: Route, dismissible: Boolean = true) {
        navigator.presentModal(
            Modal(
                route = route,
                presentationStyle = ModalPresentationStyle.SHEET,
                dismissible = dismissible
            )
        )
    }

    /**
     * Present a full-screen modal
     */
    fun presentFullScreen(route: Route, dismissible: Boolean = true) {
        navigator.presentModal(
            Modal(
                route = route,
                presentationStyle = ModalPresentationStyle.FULL_SCREEN,
                dismissible = dismissible
            )
        )
    }

    /**
     * Dismiss the current modal
     */
    fun dismissModal() {
        navigator.dismissModal()
    }

    /**
     * Get the current route
     */
    val currentRoute: Route
        get() = navigator.currentRoute

    /**
     * Check if can navigate back
     */
    val canGoBack: Boolean
        get() = navigator.canGoBack
}

/**
 * Create a NavigationController from the current Navigator
 */
@Composable
fun rememberNavigationController(): NavigationController {
    val navigator = LocalNavigator.current
    return remember(navigator) {
        NavigationController(navigator)
    }
}

/**
 * Extension function to easily access NavigationController
 */
@Composable
fun useNavigationController(): NavigationController {
    return rememberNavigationController()
}
