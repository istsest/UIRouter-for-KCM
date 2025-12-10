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
 * RouterHost manages the navigation stack and renders the current route
 */
@Composable
fun RouterHost(
    initialRoute: Route,
    routes: Map<String, @Composable (Route) -> Unit>,
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
    }
) {
    val navigationStack = remember { NavigationStack(initialRoute) }
    val modalState = rememberModalState()
    val navigator = rememberNavigator(navigationStack, modalState)

    CompositionLocalProvider(LocalNavigator provides navigator) {
        Box(modifier = modifier.fillMaxSize()) {
            AnimatedContent(
                targetState = navigationStack.currentRoute,
                transitionSpec = transitionSpec,
                modifier = Modifier.fillMaxSize(),
                label = "RouterHostAnimation"
            ) { route ->
                val content = routes[route.routeId]
                if (content != null) {
                    content(route)
                } else {
                    // Default content for unregistered routes
                    Box(modifier = Modifier.fillMaxSize())
                }
            }

            // Render modals on top
            ModalHost(
                modalState = modalState,
                routes = routes
            )
        }
    }
}

/**
 * Simple RouterHost without animations
 */
@Composable
fun SimpleRouterHost(
    initialRoute: Route,
    routes: Map<String, @Composable (Route) -> Unit>,
    modifier: Modifier = Modifier
) {
    val navigationStack = remember { NavigationStack(initialRoute) }
    val modalState = rememberModalState()
    val navigator = rememberNavigator(navigationStack, modalState)

    CompositionLocalProvider(LocalNavigator provides navigator) {
        Box(modifier = modifier.fillMaxSize()) {
            val route = navigationStack.currentRoute
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
    }
}
