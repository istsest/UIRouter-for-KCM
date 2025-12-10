package com.kcm.uirouter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

/**
 * ModalHost manages the presentation of modals
 */
@Composable
internal fun ModalHost(
    modalState: ModalState,
    routes: Map<String, @Composable (Route) -> Unit>,
    modifier: Modifier = Modifier
) {
    val currentModal = modalState.currentModal

    AnimatedVisibility(
        visible = currentModal != null,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300)),
        modifier = modifier
    ) {
        if (currentModal != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .pointerInput(currentModal.dismissible) {
                        if (currentModal.dismissible) {
                            detectTapGestures {
                                modalState.dismiss()
                            }
                        }
                    }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (currentModal.presentationStyle) {
                        ModalPresentationStyle.SHEET -> {
                            SheetModal(
                                modal = currentModal,
                                routes = routes,
                                onDismiss = { if (currentModal.dismissible) modalState.dismiss() }
                            )
                        }
                        ModalPresentationStyle.FULL_SCREEN -> {
                            FullScreenModal(
                                modal = currentModal,
                                routes = routes,
                                onDismiss = { if (currentModal.dismissible) modalState.dismiss() }
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Sheet modal presentation
 */
@Composable
private fun SheetModal(
    modal: Modal,
    routes: Map<String, @Composable (Route) -> Unit>,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            visible = true,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(300)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(300)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .pointerInput(Unit) {
                        // Consume taps to prevent dismissal when tapping on modal content
                        detectTapGestures { }
                    }
            ) {
                val content = routes[modal.route.routeId]
                if (content != null) {
                    content(modal.route)
                }
            }
        }
    }
}

/**
 * Full-screen modal presentation
 */
@Composable
private fun FullScreenModal(
    modal: Modal,
    routes: Map<String, @Composable (Route) -> Unit>,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(300)
        ) + fadeIn(animationSpec = tween(300)),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(300)
        ) + fadeOut(animationSpec = tween(300)),
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .pointerInput(Unit) {
                    // Consume taps to prevent dismissal when tapping on modal content
                    detectTapGestures { }
                }
        ) {
            val content = routes[modal.route.routeId]
            if (content != null) {
                content(modal.route)
            }
        }
    }
}
