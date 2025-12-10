package com.kcm.uirouter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

/**
 * Navigator provides programmatic navigation control
 */
@Stable
interface Navigator {
    /**
     * Navigate to a new route
     */
    fun navigate(route: Route)

    /**
     * Navigate back to the previous route
     */
    fun navigateBack(): Boolean

    /**
     * Navigate back to a specific route
     */
    fun navigateBackTo(route: Route): Boolean

    /**
     * Navigate back to the root route
     */
    fun navigateBackToRoot()

    /**
     * Replace the current route with a new route
     */
    fun replace(route: Route)

    /**
     * Replace all routes with a new route
     */
    fun replaceAll(route: Route)

    /**
     * Present a modal
     */
    fun presentModal(modal: Modal)

    /**
     * Dismiss the current modal
     */
    fun dismissModal(): Boolean

    /**
     * Get the current route
     */
    val currentRoute: Route

    /**
     * Check if can navigate back
     */
    val canGoBack: Boolean
}

/**
 * CompositionLocal for accessing Navigator
 */
val LocalNavigator = compositionLocalOf<Navigator> {
    error("Navigator not provided")
}

/**
 * Internal implementation of Navigator
 */
internal class NavigatorImpl(
    private val navigationStack: NavigationStack,
    private val modalState: ModalState
) : Navigator {

    override fun navigate(route: Route) {
        navigationStack.push(route)
    }

    override fun navigateBack(): Boolean {
        return navigationStack.pop()
    }

    override fun navigateBackTo(route: Route): Boolean {
        return navigationStack.popTo(route)
    }

    override fun navigateBackToRoot() {
        navigationStack.popToRoot()
    }

    override fun replace(route: Route) {
        navigationStack.replace(route)
    }

    override fun replaceAll(route: Route) {
        navigationStack.replaceAll(route)
    }

    override fun presentModal(modal: Modal) {
        modalState.present(modal)
    }

    override fun dismissModal(): Boolean {
        return modalState.dismiss()
    }

    override val currentRoute: Route
        get() = navigationStack.currentRoute

    override val canGoBack: Boolean
        get() = navigationStack.canGoBack
}

/**
 * Remember a Navigator instance
 */
@Composable
fun rememberNavigator(
    navigationStack: NavigationStack,
    modalState: ModalState = rememberModalState()
): Navigator {
    return remember(navigationStack, modalState) {
        NavigatorImpl(navigationStack, modalState)
    }
}
