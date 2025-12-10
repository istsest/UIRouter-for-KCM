package com.kcm.uirouter

import androidx.compose.runtime.Composable

/**
 * Base interface for all routes in the application.
 * Routes define destinations that can be navigated to.
 */
interface Route {
    /**
     * Unique identifier for this route.
     * Should be unique across all routes in the application.
     */
    val routeId: String

    /**
     * Optional parameters associated with this route.
     */
    val parameters: Map<String, Any?>
        get() = emptyMap()
}

/**
 * Extension function to create a route with parameters
 */
fun Route.withParameters(vararg params: Pair<String, Any?>): Route {
    val original = this
    return object : Route {
        override val routeId: String = original.routeId
        override val parameters: Map<String, Any?> = params.toMap()
    }
}

/**
 * Helper to get typed parameter from route
 */
inline fun <reified T> Route.getParameter(key: String): T? {
    return parameters[key] as? T
}

/**
 * Helper to get required typed parameter from route
 */
inline fun <reified T> Route.requireParameter(key: String): T {
    return parameters[key] as? T
        ?: throw IllegalArgumentException("Required parameter '$key' not found or has wrong type")
}
