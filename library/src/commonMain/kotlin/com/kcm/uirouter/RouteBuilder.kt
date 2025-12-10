package com.kcm.uirouter

import androidx.compose.runtime.Composable

/**
 * Builder for defining route to composable mappings
 */
class RouteBuilder {
    private val routes = mutableMapOf<String, @Composable (Route) -> Unit>()

    /**
     * Register a route with its composable content
     */
    fun <T : Route> route(
        routeId: String,
        content: @Composable (T) -> Unit
    ) {
        routes[routeId] = { route ->
            @Suppress("UNCHECKED_CAST")
            content(route as T)
        }
    }

    /**
     * Register a route using the route instance itself
     */
    inline fun <reified T : Route> route(
        route: T,
        noinline content: @Composable (T) -> Unit
    ) {
        route(route.routeId, content)
    }

    internal fun build(): Map<String, @Composable (Route) -> Unit> = routes.toMap()
}

/**
 * DSL function to build routes
 */
fun buildRoutes(builder: RouteBuilder.() -> Unit): Map<String, @Composable (Route) -> Unit> {
    return RouteBuilder().apply(builder).build()
}
