package com.kcm.uirouter

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Represents a stack of routes for navigation
 */
@Stable
class NavigationStack(
    initialRoute: Route
) {
    private var _stack by mutableStateOf(listOf(initialRoute))

    val stack: List<Route>
        get() = _stack

    val currentRoute: Route
        get() = _stack.last()

    val canGoBack: Boolean
        get() = _stack.size > 1

    /**
     * Push a new route onto the stack
     */
    fun push(route: Route) {
        _stack = _stack + route
    }

    /**
     * Pop the current route from the stack
     * Returns false if cannot go back
     */
    fun pop(): Boolean {
        if (!canGoBack) return false
        _stack = _stack.dropLast(1)
        return true
    }

    /**
     * Pop to a specific route in the stack
     * Returns false if route not found
     */
    fun popTo(route: Route): Boolean {
        val index = _stack.indexOfFirst { it.routeId == route.routeId }
        if (index == -1 || index == _stack.lastIndex) return false
        _stack = _stack.take(index + 1)
        return true
    }

    /**
     * Pop to the root route
     */
    fun popToRoot() {
        if (_stack.isEmpty()) return
        _stack = listOf(_stack.first())
    }

    /**
     * Replace the current route with a new route
     */
    fun replace(route: Route) {
        if (_stack.isEmpty()) {
            _stack = listOf(route)
        } else {
            _stack = _stack.dropLast(1) + route
        }
    }

    /**
     * Replace the entire stack with a new route
     */
    fun replaceAll(route: Route) {
        _stack = listOf(route)
    }

    /**
     * Clear the stack and set a new root
     */
    fun setRoot(route: Route) {
        _stack = listOf(route)
    }
}
