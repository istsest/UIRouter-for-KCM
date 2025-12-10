# Changelog

All notable changes to UIRouter for KCM will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.0.0] - 2025-12-09

### Added
- Initial release of UIRouter for KCM
- Type-safe routing with protocol-based routes
- Single-stack navigation with `RouterHost`
- Tab-based navigation with `TabRouterHost`
- Modal presentations (sheet and full-screen)
- Programmatic navigation control via `NavigationController`
- Animated transitions between routes
- Back button handling
- Cross-platform support for Android and iOS
- Comprehensive documentation and examples

### Components
- `Route` - Base protocol for defining routes
- `Navigator` - Core navigation interface
- `NavigationController` - High-level navigation API
- `NavigationStack` - Stack state management
- `RouterHost` - Main router composable
- `TabNavigator` - Tab navigation management
- `TabRouterHost` - Tab-based router composable
- `Modal` and `ModalState` - Modal presentation system
- `BackHandler` and `BackButton` - Back navigation support

### Technical Details
- Package: `com.kcm.uirouter`
- iOS Framework: `UIRouterKCM`
- Min SDK: Android 24, iOS 13
- Kotlin: 2.1.0
- Compose Multiplatform: 1.7.3

---

## Release Notes Template

### [X.Y.Z] - YYYY-MM-DD

#### Added
- New features

#### Changed
- Changes in existing functionality

#### Deprecated
- Soon-to-be removed features

#### Removed
- Removed features

#### Fixed
- Bug fixes

#### Security
- Security improvements

---

[Unreleased]: https://github.com/yourusername/uirouter-for-kcm/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/yourusername/uirouter-for-kcm/releases/tag/v1.0.0
