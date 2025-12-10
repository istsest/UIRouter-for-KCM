package com.kcm.uirouter

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

/**
 * CompositionLocal for accessing TabNavigator
 */
import androidx.compose.runtime.compositionLocalOf

val LocalTabNavigator = compositionLocalOf<TabNavigator> {
    error("TabNavigator not provided")
}

/**
 * TabRouterHost manages tab-based navigation with independent stacks
 */
@Composable
fun TabRouterHost(
    tabs: List<Tab>,
    routes: Map<String, @Composable (Route) -> Unit>,
    initialTabId: String = tabs.first().id,
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentTransitionScope<Route>.() -> ContentTransform = {
        (slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(300)
        ) + fadeIn(animationSpec = tween(300))).togetherWith(
            slideOutHorizontally(
                targetOffsetX = { -it / 3 },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        )
    },
    tabContent: @Composable (TabNavigator) -> Unit
) {
    val tabNavigator = remember(tabs, initialTabId) {
        TabNavigator(tabs, initialTabId)
    }

    val modalState = rememberModalState()

    CompositionLocalProvider(
        LocalTabNavigator provides tabNavigator
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            // Render the active tab's navigation stack
            val currentStack = tabNavigator.currentStack
            val navigator = rememberNavigator(currentStack, modalState)

            CompositionLocalProvider(LocalNavigator provides navigator) {
                AnimatedContent(
                    targetState = currentStack.currentRoute,
                    transitionSpec = transitionSpec,
                    modifier = Modifier.fillMaxSize(),
                    label = "TabRouterHostAnimation"
                ) { route ->
                    val content = routes[route.routeId]
                    if (content != null) {
                        content(route)
                    } else {
                        Box(modifier = Modifier.fillMaxSize())
                    }
                }

                // Render modals on top
                ModalHost(
                    modalState = modalState,
                    routes = routes
                )
            }

            // Render tab bar or custom tab UI
            tabContent(tabNavigator)
        }
    }
}

/**
 * Simple TabRouterHost without animations
 */
@Composable
fun SimpleTabRouterHost(
    tabs: List<Tab>,
    routes: Map<String, @Composable (Route) -> Unit>,
    initialTabId: String = tabs.first().id,
    modifier: Modifier = Modifier,
    tabContent: @Composable (TabNavigator) -> Unit
) {
    val tabNavigator = remember(tabs, initialTabId) {
        TabNavigator(tabs, initialTabId)
    }

    val modalState = rememberModalState()

    CompositionLocalProvider(
        LocalTabNavigator provides tabNavigator
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            val currentStack = tabNavigator.currentStack
            val navigator = rememberNavigator(currentStack, modalState)

            CompositionLocalProvider(LocalNavigator provides navigator) {
                val route = currentStack.currentRoute
                val content = routes[route.routeId]
                if (content != null) {
                    content(route)
                }

                // Render modals on top
                ModalHost(
                    modalState = modalState,
                    routes = routes
                )
            }

            // Render tab bar or custom tab UI
            tabContent(tabNavigator)
        }
    }
}
