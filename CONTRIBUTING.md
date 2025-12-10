# Contributing to UIRouter for KCM

First off, thank you for considering contributing to UIRouter for KCM!

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the issue list as you might find out that you don't need to create one. When you are creating a bug report, please include as many details as possible:

* **Use a clear and descriptive title**
* **Describe the exact steps which reproduce the problem**
* **Provide specific examples to demonstrate the steps**
* **Describe the behavior you observed and what behavior you expected**
* **Include code samples and stack traces if relevant**
* **Note your environment details** (OS, Kotlin version, Compose version, etc.)

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion, please include:

* **Use a clear and descriptive title**
* **Provide a step-by-step description of the suggested enhancement**
* **Provide specific examples to demonstrate the steps**
* **Describe the current behavior** and **explain the behavior you expected to see**
* **Explain why this enhancement would be useful**

### Pull Requests

* Fill in the required template
* Do not include issue numbers in the PR title
* Follow the Kotlin coding style
* Include thoughtfully-worded, well-structured tests
* Document new code with KDoc comments
* End all files with a newline

## Development Setup

### Prerequisites

* JDK 17 or higher
* Android Studio or IntelliJ IDEA
* Xcode 15+ (for iOS development)

### Building the Project

```bash
# Clone the repository
git clone https://github.com/yourusername/uirouter-for-kcm.git
cd uirouter-for-kcm

# Build the project
./gradlew build

# Run tests
./gradlew test

# Publish to local Maven
./gradlew publishToMavenLocal
```

### Project Structure

```
uirouter-for-kcm/
├── library/                    # Main library module
│   └── src/
│       ├── commonMain/         # Shared code
│       ├── androidMain/        # Android-specific code
│       └── iosMain/            # iOS-specific code
├── samples/                    # Sample projects (optional)
└── docs/                       # Documentation
```

### Code Style

* Follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
* Use meaningful variable and function names
* Write KDoc comments for public APIs
* Keep functions small and focused
* Write tests for new features

### Testing

* Write unit tests for new features
* Ensure all tests pass before submitting PR
* Aim for high code coverage
* Test on both Android and iOS when possible

```bash
# Run all tests
./gradlew test

# Run Android tests
./gradlew :library:testDebugUnitTest

# Run iOS tests
./gradlew :library:iosTest
```

### Documentation

* Update README.md if you change public APIs
* Add KDoc comments to public classes and functions
* Include code examples in documentation
* Update CHANGELOG.md with your changes

## Git Commit Messages

* Use the present tense ("Add feature" not "Added feature")
* Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
* Limit the first line to 72 characters or less
* Reference issues and pull requests liberally after the first line

Examples:
```
Add tab navigation support

Implement TabNavigator and TabRouterHost to support multiple
independent navigation stacks.

Closes #123
```

## Code Review Process

* All submissions require review
* We may ask for changes before merging
* We'll provide constructive feedback
* Be patient - review may take a few days

## Community

* Be respectful and constructive
* Welcome newcomers and encourage diverse perspectives
* Focus on what is best for the community
* Show empathy towards other community members

## License

By contributing, you agree that your contributions will be licensed under the MIT License.

## Questions?

Feel free to open an issue with your question or reach out in Discussions.

Thank you for contributing! 🎉
