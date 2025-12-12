#!/bin/bash

# UIRouter for KCM - GitHub Pages Maven Repository Setup Script
# This script creates and configures the GitHub Pages repository for Maven artifacts

set -e  # Exit on error

# Configuration
REPO_NAME="uirouter-kcm-maven"
MAVEN_REPO_DIR="$HOME/$REPO_NAME"
GITHUB_USER="istsest"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Print colored message
print_info() {
    echo -e "${BLUE}ℹ ${NC}$1"
}

print_success() {
    echo -e "${GREEN}✓${NC} $1"
}

print_error() {
    echo -e "${RED}✗${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}⚠${NC} $1"
}

print_header() {
    echo ""
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo -e "${GREEN}$1${NC}"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo ""
}

print_header "UIRouter for KCM - GitHub Pages Maven Setup"

# Check if gh CLI is installed
if ! command -v gh &> /dev/null; then
    print_error "GitHub CLI (gh) is not installed"
    print_info "Install it with: brew install gh"
    exit 1
fi

# Check if user is logged in to GitHub CLI
if ! gh auth status &> /dev/null; then
    print_error "Not logged in to GitHub CLI"
    print_info "Please run: gh auth login"
    exit 1
fi

print_success "GitHub CLI is ready"
echo ""

# Step 1: Create local Maven repository directory
print_info "Step 1/4: Creating local Maven repository directory..."

if [ -d "$MAVEN_REPO_DIR" ]; then
    print_warning "Directory already exists: $MAVEN_REPO_DIR"
    read -p "Do you want to delete and recreate it? (y/N): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        rm -rf "$MAVEN_REPO_DIR"
        print_info "Deleted existing directory"
    else
        print_error "Setup cancelled"
        exit 1
    fi
fi

mkdir -p "$MAVEN_REPO_DIR"
cd "$MAVEN_REPO_DIR"
print_success "Directory created: $MAVEN_REPO_DIR"
echo ""

# Step 2: Initialize Git repository
print_info "Step 2/4: Initializing Git repository..."

git init
git branch -M main

# Create README
cat > README.md << 'EOF'
# UIRouter for KCM - Maven Repository

This repository serves as a Maven repository for [UIRouter for KCM](https://github.com/istsest/UIRouter-for-KCM), a type-safe routing library for Kotlin Compose Multiplatform.

## Usage

Add this repository to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://istsest.github.io/uirouter-kcm-maven")
        }
    }
}
```

Then add the dependency:

```kotlin
dependencies {
    implementation("io.github.istsest:uirouter-for-kcm:VERSION")
}
```

## Platform Support

- ✅ Android
- ✅ iOS (arm64, x64, Simulator arm64)
- ✅ Kotlin Multiplatform

## License

This repository serves as a distribution mechanism. See the main project for license information.

---

**Note**: This repository is automatically generated. Do not edit manually.
EOF

# Create .gitignore
cat > .gitignore << 'EOF'
.DS_Store
*.swp
*.tmp
EOF

git add .
git commit -m "Initial commit - Maven repository setup"

print_success "Git repository initialized"
echo ""

# Step 3: Create GitHub repository
print_info "Step 3/4: Creating GitHub repository..."

if gh repo view "$GITHUB_USER/$REPO_NAME" &> /dev/null; then
    print_warning "Repository $GITHUB_USER/$REPO_NAME already exists on GitHub"
    read -p "Do you want to use the existing repository? (Y/n): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Nn]$ ]]; then
        print_info "Using existing repository"
    else
        print_error "Setup cancelled"
        exit 1
    fi
else
    gh repo create "$GITHUB_USER/$REPO_NAME" --public --source=. --remote=origin --description="Maven repository for UIRouter-for-KCM library"
    print_success "GitHub repository created"
fi

echo ""

# Step 4: Enable GitHub Pages
print_info "Step 4/4: Enabling GitHub Pages..."

# Push initial commit
git push -u origin main

# Enable GitHub Pages using gh CLI
gh api \
  --method POST \
  -H "Accept: application/vnd.github+json" \
  "/repos/$GITHUB_USER/$REPO_NAME/pages" \
  -f "source[branch]=main" \
  -f "source[path]=/" \
  2>&1 | grep -q "Unprocessable Entity" && print_warning "GitHub Pages may already be enabled" || print_success "GitHub Pages enabled"

echo ""
print_header "Setup Complete!"

print_success "Maven repository is ready!"
print_info "Repository location: ${BLUE}$MAVEN_REPO_DIR${NC}"
print_info "GitHub repository: ${BLUE}https://github.com/$GITHUB_USER/$REPO_NAME${NC}"
print_info "Maven URL: ${GREEN}https://$GITHUB_USER.github.io/$REPO_NAME${NC}"
echo ""
print_warning "GitHub Pages may take 1-2 minutes to become active"
echo ""
print_info "Next steps:"
echo "  1. Wait for GitHub Pages to be deployed"
echo "  2. Run ${YELLOW}./deploy-to-pages.sh${NC} to publish your library"
echo ""
print_info "You can check GitHub Pages status at:"
echo "  ${BLUE}https://github.com/$GITHUB_USER/$REPO_NAME/settings/pages${NC}"
echo ""
