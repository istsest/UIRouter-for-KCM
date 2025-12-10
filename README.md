# UIRouter for KCM

A powerful, type-safe routing library for Kotlin Compose Multiplatform.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![](https://jitpack.io/v/yourusername/uirouter-for-kcm.svg)](https://jitpack.io/#yourusername/uirouter-for-kcm)

## Features

- ✅ **Type-safe routing** with protocol-based routes
- ✅ **Single-stack navigation** with animated transitions
- ✅ **Tab-based navigation** with independent stacks per tab
- ✅ **Modal presentations** (sheet and full-screen)
- ✅ **Programmatic navigation** control
- ✅ **Cross-platform** support (iOS and Android)
- ✅ **Lightweight** and easy to integrate

## Installation

Add JitPack repository to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Add the dependency to your shared module:

```kotlin
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.github.istsest:uirouter-for-kcm:0.0.1")
            }
        }
    }
}
```

## Quick Start

### Step 1: Define Routes

```kotlin
import com.kcm.uirouter.Route

sealed class AppRoute(override val routeId: String) : Route {
    data object Home : AppRoute("home")
    data object Profile : AppRoute("profile")

    data class Details(val id: String) : AppRoute("details") {
        override val parameters = mapOf("id" to id)
    }
}
```

### Step 2: Build Route Mappings

```kotlin
import com.kcm.uirouter.*

val routes = buildRoutes {
    route(AppRoute.Home) {
        HomeScreen()
    }

    route(AppRoute.Profile) {
        ProfileScreen()
    }

    route<AppRoute.Details>("details") { route ->
        val id = route.requireParameter<String>("id")
        DetailsScreen(id)
    }
}
```

### Step 3: Set Up RouterHost

```kotlin
@Composable
fun App() {
    RouterHost(
        initialRoute = AppRoute.Home,
        routes = routes
    )
}
```

### Step 4: Navigate

```kotlin
@Composable
fun HomeScreen() {
    val navigator = rememberNavigationController()

    Button(onClick = { navigator.push(AppRoute.Profile) }) {
        Text("Go to Profile")
    }

    Button(onClick = { navigator.push(AppRoute.Details("123")) }) {
        Text("View Details")
    }
}
```

## Navigation Operations

```kotlin
val navigator = rememberNavigationController()

// Push a new route
navigator.push(AppRoute.Profile)

// Pop back
navigator.pop()

// Pop to specific route
navigator.popTo(AppRoute.Home)

// Pop to root
navigator.popToRoot()

// Replace current route
navigator.replace(AppRoute.Settings)

// Replace entire stack
navigator.replaceAll(AppRoute.Home)

// Check navigation state
if (navigator.canGoBack) {
    navigator.pop()
}
```

## Tab Navigation

```kotlin
@Composable
fun TabbedApp() {
    val tabs = listOf(
        Tab("home", AppRoute.Home),
        Tab("search", AppRoute.Search),
        Tab("profile", AppRoute.Profile)
    )

    TabRouterHost(
        tabs = tabs,
        routes = routes,
        initialTabId = "home"
    ) { tabNavigator ->
        BottomNavigationBar(
            currentTabId = tabNavigator.currentTabId,
            onTabClick = { tabNavigator.switchTab(it) }
        )
    }
}
```

## Modal Presentations

```kotlin
val navigator = rememberNavigationController()

// Present sheet modal
navigator.presentSheet(
    route = AppRoute.Settings,
    dismissible = true
)

// Present full-screen modal
navigator.presentFullScreen(
    route = AppRoute.ImageViewer,
    dismissible = true
)

// Dismiss modal
navigator.dismissModal()
```

## Advanced Features

### Route Parameters

Type-safe parameter passing and retrieval:

```kotlin
data class UserProfile(
    val userId: String,
    val showDetails: Boolean = false
) : Route {
    override val routeId = "user_profile"
    override val parameters = mapOf(
        "userId" to userId,
        "showDetails" to showDetails
    )
}

// Navigate with parameters
navigator.push(UserProfile(userId = "123", showDetails = true))

// Retrieve parameters
route<UserProfile>("user_profile") { route ->
    val userId = route.requireParameter<String>("userId")
    val showDetails = route.getParameter<Boolean>("showDetails") ?: false
    UserProfileScreen(userId, showDetails)
}
```

### Custom Transitions

```kotlin
RouterHost(
    initialRoute = AppRoute.Home,
    routes = routes,
    transitionSpec = {
        slideInHorizontally(initialOffsetX = { it })
            .togetherWith(slideOutHorizontally(targetOffsetX = { -it }))
    }
)
```

### Back Handler

```kotlin
@Composable
fun MyScreen() {
    BackHandler(enabled = true) {
        // Custom back handling
    }

    // Or use BackButton composable
    BackButton { onClick, canGoBack ->
        IconButton(onClick = onClick, enabled = canGoBack) {
            Icon(Icons.Default.ArrowBack, "Back")
        }
    }
}
```

## Architecture

UIRouter for KCM follows a clean, layered architecture:

```
┌─────────────────────────────────┐
│      Application Layer          │  Your app code
├─────────────────────────────────┤
│      High-Level API             │  NavigationController, RouterHost
├─────────────────────────────────┤
│      Core Layer                 │  Navigator, TabNavigator
├─────────────────────────────────┤
│      State Layer                │  NavigationStack, ModalState
└─────────────────────────────────┘
```

### Core Components

- **Route**: Protocol interface for defining destinations
- **Navigator**: Core navigation interface
- **NavigationController**: High-level, convenient API
- **NavigationStack**: Manages route stack
- **RouterHost**: Main router composable
- **TabNavigator**: Manages multiple stacks for tabs
- **ModalState**: Handles modal presentations

## API Reference

### Composables

- `RouterHost()` - Single-stack router with animations
- `SimpleRouterHost()` - Single-stack router without animations
- `TabRouterHost()` - Tab-based router with animations
- `SimpleTabRouterHost()` - Tab-based router without animations
- `BackHandler()` - Handle back button events
- `BackButton()` - Custom back button component

### Functions

- `buildRoutes()` - DSL for defining routes
- `rememberNavigator()` - Get Navigator instance
- `rememberNavigationController()` - Get NavigationController
- `rememberModalState()` - Get ModalState instance

### Classes

- `Route` - Base route interface
- `NavigationStack` - Stack state management
- `Navigator` - Navigation operations
- `NavigationController` - High-level navigation API
- `TabNavigator` - Tab navigation management
- `Modal` - Modal presentation data
- `ModalState` - Modal state management

## Platform Support

- **Android**: minSdk 24, compileSdk 35
- **iOS**: iosX64, iosArm64, iosSimulatorArm64
- **Framework**: UIRouterKCM.framework for iOS

## Requirements

- Kotlin 2.1.0+
- Compose Multiplatform 1.7.3+
- Android Gradle Plugin 8.7.3+ (for Android)
- Xcode 15+ (for iOS)

## Sample Projects

Check out the `samples` directory for complete example projects.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

```
MIT License

Copyright (c) 2025 UIRouter for KCM Contributors

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Support

- 📖 [Documentation](https://github.com/yourusername/uirouter-for-kcm/wiki)
- 🐛 [Issue Tracker](https://github.com/yourusername/uirouter-for-kcm/issues)
- 💬 [Discussions](https://github.com/yourusername/uirouter-for-kcm/discussions)

---

Made with ❤️ for Kotlin Compose Multiplatform
