package com.kcm.uirouter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState

/**
 * BackHandler allows handling back navigation events
 */
@Composable
fun BackHandler(
    enabled: Boolean = true,
    onBack: () -> Unit
) {
    val currentOnBack by rememberUpdatedState(onBack)
    val navigator = LocalNavigator.current

    DisposableEffect(enabled) {
        if (enabled) {
            // This is a simplified version
            // In a production app, you'd integrate with platform-specific back handlers
            onDispose { }
        } else {
            onDispose { }
        }
    }
}

/**
 * BackButton component for manual back navigation
 */
@Composable
fun BackButton(
    enabled: Boolean = true,
    content: @Composable (() -> Unit, Boolean) -> Unit
) {
    val navigator = LocalNavigator.current
    val canGoBack = navigator.canGoBack && enabled

    content(
        {
            if (canGoBack) {
                navigator.navigateBack()
            }
        },
        canGoBack
    )
}
