# iOS Sample App Setup Guide

## Quick Setup (Recommended)

### Option 1: Use Kotlin Multiplatform Plugin (Easiest)

1. Open Android Studio or IntelliJ IDEA
2. Install "Kotlin Multiplatform" plugin if not already installed
3. Open the project root directory
4. In the Gradle tool window, find: `sample > Tasks > kotlin > iosSimulatorArm64MainKlibrary`
5. Run the task to generate the iOS framework
6. Click the "Run iOS" button in the IDE toolbar

### Option 2: Manual Xcode Project Creation

1. **Generate the iOS Framework**
   ```bash
   cd /Users/istsest/MacProjects/Joon/UIRouter-for-KCM
   ./gradlew :sample:linkDebugFrameworkIosSimulatorArm64
   ```

2. **Create Xcode Project**
   - Open Xcode
   - Create new iOS App project
   - Product Name: `SampleApp`
   - Bundle Identifier: `com.kcm.uirouter.sample`
   - Interface: SwiftUI
   - Language: Swift
   - Minimum Deployments: iOS 16.0
   - Save in: `/Users/istsest/MacProjects/Joon/UIRouter-for-KCM/sample/iosApp`

3. **Link the Framework**
   - Select project in navigator
   - Select SampleApp target
   - Go to "General" tab
   - Scroll to "Frameworks, Libraries, and Embedded Content"
   - Click "+" button
   - Click "Add Other..." → "Add Files..."
   - Navigate to: `sample/build/bin/iosSimulatorArm64/debugFramework/SampleApp.framework`
   - Select "Copy items if needed"
   - Embed: "Embed & Sign"

4. **Add Files**
   - Replace `ContentView.swift` with the provided `iOSApp.swift`
   - Or add the `iOSApp.swift` content to your main App file

5. **Build and Run**
   - Select iPhone simulator (iOS 16.0+)
   - Press Cmd+R to build and run

## Building from Command Line

### For Simulator (Apple Silicon Mac)
```bash
./gradlew :sample:linkDebugFrameworkIosSimulatorArm64
```

### For Simulator (Intel Mac)
```bash
./gradlew :sample:linkDebugFrameworkIosX64
```

### For Device
```bash
./gradlew :sample:linkDebugFrameworkIosArm64
```

## Project Structure

```
sample/
├── build.gradle.kts                    # Gradle configuration
├── src/
│   ├── commonMain/                     # Shared Kotlin code
│   ├── androidMain/                    # Android-specific
│   └── iosMain/                        # iOS-specific
└── iosApp/                             # iOS app wrapper
    └── iosApp/
        ├── iOSApp.swift                # SwiftUI entry point
        └── Info.plist                  # iOS app configuration
```

## Troubleshooting

### Framework not found
```bash
# Clean and rebuild
./gradlew clean
./gradlew :sample:linkDebugFrameworkIosSimulatorArm64
```

### Module 'SampleApp' not found
- Ensure framework is properly linked in Xcode
- Check Framework Search Paths in Build Settings
- Clean Xcode build folder (Shift+Cmd+K)

### iOS Version Issues
- Minimum deployment target is iOS 16.0
- Update in Xcode: Project → General → Minimum Deployments

### Build Phase Issues
If you get linker errors:
1. Select project in Xcode
2. Go to Build Phases
3. Add "Run Script" phase BEFORE "Compile Sources":
   ```bash
   cd "$SRCROOT/.."
   ./gradlew :sample:linkDebugFrameworkIosSimulatorArm64
   ```

## Testing on Real Device

1. Connect your iOS device (iOS 16.0+)
2. Build the framework for device:
   ```bash
   ./gradlew :sample:linkDebugFrameworkIosArm64
   ```
3. In Xcode, select your device from the device list
4. Signing: Select your development team
5. Run (Cmd+R)

## Hot Reload

For development with hot reload:
1. Keep the Gradle task running:
   ```bash
   ./gradlew :sample:linkDebugFrameworkIosSimulatorArm64 --continuous
   ```
2. Make changes to Kotlin code
3. Rebuild in Xcode (Cmd+B)

## Production Build

```bash
# Build release framework
./gradlew :sample:linkReleaseFrameworkIosArm64

# Framework will be at:
# sample/build/bin/iosArm64/releaseFramework/SampleApp.framework
```

## Useful Commands

```bash
# List all Gradle tasks
./gradlew :sample:tasks

# Run tests
./gradlew :sample:iosTest

# Clean build
./gradlew :sample:clean

# Full rebuild
./gradlew clean :sample:linkDebugFrameworkIosSimulatorArm64
```

## Need Help?

- Check main [README.md](../README.md) for library documentation
- See [sample/README.md](README.md) for app features
- Open an issue on GitHub for support
