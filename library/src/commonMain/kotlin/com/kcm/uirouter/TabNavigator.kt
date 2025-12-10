package com.kcm.uirouter

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Represents a tab in tab-based navigation
 */
data class Tab(
    val id: String,
    val initialRoute: Route
)

/**
 * TabNavigator manages multiple independent navigation stacks for tabs
 */
@Stable
class TabNavigator(
    private val tabs: List<Tab>,
    initialTabId: String = tabs.first().id
) {
    private val _stacks = mutableMapOf<String, NavigationStack>().apply {
        tabs.forEach { tab ->
            put(tab.id, NavigationStack(tab.initialRoute))
        }
    }

    var currentTabId by mutableStateOf(
        if (tabs.any { it.id == initialTabId }) initialTabId else tabs.first().id
    )
        private set

    val currentTab: Tab
        get() = tabs.first { it.id == currentTabId }

    val currentStack: NavigationStack
        get() = _stacks[currentTabId]!!

    /**
     * Switch to a different tab
     */
    fun switchTab(tabId: String) {
        if (tabs.any { it.id == tabId }) {
            currentTabId = tabId
        }
    }

    /**
     * Get navigation stack for a specific tab
     */
    fun getStack(tabId: String): NavigationStack? {
        return _stacks[tabId]
    }

    /**
     * Reset a tab's navigation stack to its initial route
     */
    fun resetTab(tabId: String) {
        val tab = tabs.firstOrNull { it.id == tabId } ?: return
        _stacks[tabId] = NavigationStack(tab.initialRoute)
    }

    /**
     * Reset all tabs to their initial routes
     */
    fun resetAllTabs() {
        tabs.forEach { tab ->
            _stacks[tab.id] = NavigationStack(tab.initialRoute)
        }
    }
}
