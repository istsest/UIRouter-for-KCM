package com.kcm.uirouter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Represents a modal presentation style
 */
enum class ModalPresentationStyle {
    /**
     * Sheet modal that covers part of the screen (bottom sheet style)
     */
    SHEET,

    /**
     * Full-screen modal that covers the entire screen
     */
    FULL_SCREEN
}

/**
 * Represents a modal that can be presented
 */
data class Modal(
    val route: Route,
    val presentationStyle: ModalPresentationStyle = ModalPresentationStyle.SHEET,
    val dismissible: Boolean = true
)

/**
 * State management for modals
 */

@Stable
class ModalState {
    private var _modalStack by mutableStateOf<List<Modal>>(emptyList())

    val modalStack: List<Modal>
        get() = _modalStack

    val currentModal: Modal?
        get() = _modalStack.lastOrNull()

    val hasModal: Boolean
        get() = _modalStack.isNotEmpty()

    /**
     * Present a new modal
     */
    fun present(modal: Modal) {
        _modalStack = _modalStack + modal
    }

    /**
     * Dismiss the current modal
     * Returns false if no modal to dismiss
     */
    fun dismiss(): Boolean {
        if (_modalStack.isEmpty()) return false
        _modalStack = _modalStack.dropLast(1)
        return true
    }

    /**
     * Dismiss all modals
     */
    fun dismissAll() {
        _modalStack = emptyList()
    }
}

/**
 * Remember a ModalState instance
 */
@Composable
fun rememberModalState(): ModalState {
    return androidx.compose.runtime.remember { ModalState() }
}
