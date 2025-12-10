# UIRouter for KCM - Sample App

This is a complete demonstration app showing all features of UIRouter for KCM.

## Features Demonstrated

### ✅ Tab Navigation
- **3 tabs**: Home, Explore, Profile
- Each tab maintains its own independent navigation stack
- Switch between tabs while preserving navigation state

### ✅ Push/Pop Navigation
- Navigate forward with `push()`
- Navigate back with `pop()`
- Pop to root with `popToRoot()`
- Pop to specific route with `popTo()`

### ✅ Modal Presentations

#### Sheet Modal
- Settings screen presented as a sheet
- Dismissible by tapping outside or using close button
- Demonstrates `presentSheet()` and `dismissModal()`

#### Full-Screen Modal
- Image viewer presented full-screen
- Custom dismiss button
- Demonstrates `presentFullScreen()` and `dismissModal()`

### ✅ Route Parameters
- Type-safe parameter passing
- Examples:
  - Details screen receives `id` and `title`
  - ItemList screen receives `category`
  - ImageViewer receives `imageUrl`

## Running the Sample

### Android (API 34+)

```bash
# From project root
./gradlew :sample:installDebug

# Or open in Android Studio and run
```

### iOS (iOS 16+)

1. Open `sample/iosApp/iosApp.xcodeproj` in Xcode
2. Select target device or simulator
3. Press Run (Cmd+R)

Or using command line:
```bash
cd sample/iosApp
xcodebuild -project iosApp.xcodeproj -scheme iosApp -destination 'platform=iOS Simulator,name=iPhone 15'
```

## Project Structure

```
sample/
├── src/
│   ├── commonMain/kotlin/com/kcm/uirouter/sample/
│   │   ├── SampleApp.kt              # Main app with TabRouterHost
│   │   ├── Routes.kt                 # Route definitions
│   │   ├── screens/
│   │   │   ├── HomeScreen.kt         # Home tab - list of items
│   │   │   ├── DetailsScreen.kt      # Details with push/pop demo
│   │   │   ├── ExploreScreen.kt      # Explore tab - grid of categories
│   │   │   ├── ItemListScreen.kt     # Category items list
│   │   │   ├── ProfileScreen.kt      # Profile tab
│   │   │   ├── EditProfileScreen.kt  # Edit profile screen
│   │   │   ├── SettingsScreen.kt     # Settings sheet modal
│   │   │   └── ImageViewerScreen.kt  # Full-screen image viewer
│   │   └── components/
│   │       └── BottomNavigationBar.kt
│   ├── androidMain/
│   │   └── kotlin/com/kcm/uirouter/sample/
│   │       └── MainActivity.kt
│   └── iosMain/
│       └── kotlin/com/kcm/uirouter/sample/
│           └── Main.kt
└── iosApp/
    └── iosApp/
        ├── iOSApp.swift
        └── Info.plist
```

## Navigation Flow

### Home Tab
```
Home
  ├─> Details (push)
  │     └─> (pop back to Home)
  ├─> Settings (sheet modal)
  │     └─> (dismiss modal)
  └─> Image Viewer (full-screen modal)
        └─> (dismiss modal)
```

### Explore Tab
```
Explore
  └─> ItemList (push with category)
        └─> (pop back to Explore)
```

### Profile Tab
```
Profile
  ├─> Edit Profile (push)
  │     └─> (pop back to Profile)
  └─> Settings (sheet modal)
        └─> (dismiss modal)
```

## Code Highlights

### Route Definition
```kotlin
sealed class AppRoute(override val routeId: String) : Route {
    data object Home : AppRoute("home")
    data class Details(val id: String, val title: String) : AppRoute("details") {
        override val parameters = mapOf("id" to id, "title" to title)
    }
}
```

### Navigation Usage
```kotlin
val navigator = rememberNavigationController()

// Push
navigator.push(AppRoute.Details("123", "Item Title"))

// Pop
navigator.pop()

// Present Modal
navigator.presentSheet(AppRoute.Settings, dismissible = true)
navigator.presentFullScreen(AppRoute.ImageViewer("url"), dismissible = true)

// Dismiss Modal
navigator.dismissModal()
```

### Tab Setup
```kotlin
val tabs = listOf(
    Tab("home", AppRoute.Home),
    Tab("explore", AppRoute.Explore),
    Tab("profile", AppRoute.Profile)
)

TabRouterHost(tabs, routes, "home") { tabNavigator ->
    BottomNavigationBar(
        currentTabId = tabNavigator.currentTabId,
        onTabSelected = { tabNavigator.switchTab(it) }
    )
}
```

## Testing Checklist

- [ ] Switch between tabs - navigation state preserved
- [ ] Navigate forward and back in each tab
- [ ] Open sheet modal (Settings)
- [ ] Dismiss sheet modal by tapping outside
- [ ] Dismiss sheet modal using button
- [ ] Open full-screen modal (Image Viewer)
- [ ] Dismiss full-screen modal
- [ ] Push multiple screens and use `popToRoot()`
- [ ] Rotate device - state preserved
- [ ] Test on both iOS and Android

## Requirements

- **Android**: API 34+ (Android 14+)
- **iOS**: iOS 16.0+
- **Kotlin**: 2.1.0
- **Compose Multiplatform**: 1.7.3

## Learn More

See the main [README.md](../README.md) for full UIRouter documentation.
