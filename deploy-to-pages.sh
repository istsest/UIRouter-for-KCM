#!/bin/bash

# UIRouter for KCM - GitHub Pages Maven Repository Deployment Script
# This script builds the library and deploys it to GitHub Pages Maven repository

set -e  # Exit on error

# Configuration
MAVEN_REPO_DIR="$HOME/uirouter-kcm-maven"
GROUP_PATH="io/github/istsest"
ARTIFACT_NAME="uirouter-for-kcm"

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

# Check if Maven repository directory exists
if [ ! -d "$MAVEN_REPO_DIR" ]; then
    print_error "Maven repository directory not found: $MAVEN_REPO_DIR"
    print_info "Please run setup first: ./setup-github-pages.sh"
    exit 1
fi

# Get version from build.gradle.kts
VERSION=$(grep 'version = ' build.gradle.kts | sed 's/.*version = "\(.*\)"/\1/')

if [ -z "$VERSION" ]; then
    print_error "Could not determine version from build.gradle.kts"
    exit 1
fi

print_info "Deploying UIRouter for KCM version: ${YELLOW}$VERSION${NC}"
echo ""

# Step 1: Clean previous builds
print_info "Step 1/5: Cleaning previous builds..."
./gradlew clean
print_success "Clean completed"
echo ""

# Step 2: Build and publish to local Maven
print_info "Step 2/5: Building library for all platforms (Android + iOS)..."
print_warning "This may take a few minutes..."
./gradlew :uirouter-for-kcm:publishToMavenLocal --no-daemon

if [ $? -eq 0 ]; then
    print_success "Build completed successfully"
else
    print_error "Build failed"
    exit 1
fi
echo ""

# Step 3: Copy artifacts to GitHub Pages Maven repository
print_info "Step 3/5: Copying artifacts to GitHub Pages repository..."

# Create directory structure
mkdir -p "$MAVEN_REPO_DIR/$GROUP_PATH"

# Copy main artifact
SOURCE_DIR="$HOME/.m2/repository/$GROUP_PATH/$ARTIFACT_NAME"

if [ ! -d "$SOURCE_DIR" ]; then
    print_error "Source artifacts not found at: $SOURCE_DIR"
    exit 1
fi

# Copy main artifact and ALL platform-specific artifacts (android, iosarm64, iosx64, iossimulatorarm64, etc.)
print_info "Copying main artifact and platform-specific artifacts..."
cp -r "$HOME/.m2/repository/$GROUP_PATH"/uirouter-for-kcm* "$MAVEN_REPO_DIR/$GROUP_PATH/"

if [ $? -eq 0 ]; then
    print_success "Artifacts copied successfully"
    
    # List copied artifacts
    print_info "Copied artifacts:"
    ls -1 "$MAVEN_REPO_DIR/$GROUP_PATH" | grep uirouter-for-kcm | sed 's/^/  - /'
else
    print_error "Failed to copy artifacts"
    exit 1
fi
echo ""

# Step 4: Update repository metadata
print_info "Step 4/5: Updating repository metadata..."
cd "$MAVEN_REPO_DIR"

# Create maven-metadata.xml for each artifact (main and platform-specific)
for artifact_dir in "$MAVEN_REPO_DIR/$GROUP_PATH"/uirouter-for-kcm*; do
    if [ -d "$artifact_dir" ]; then
        artifact_name=$(basename "$artifact_dir")
        
        cat > "$artifact_dir/maven-metadata.xml" << EOF
<?xml version="1.0" encoding="UTF-8"?>
<metadata>
  <groupId>io.github.istsest</groupId>
  <artifactId>$artifact_name</artifactId>
  <versioning>
    <latest>$VERSION</latest>
    <release>$VERSION</release>
    <versions>
EOF
        
        # Add all version directories
        for version_dir in "$artifact_dir"/*/; do
            if [ -d "$version_dir" ]; then
                version=$(basename "$version_dir")
                echo "      <version>$version</version>" >> "$artifact_dir/maven-metadata.xml"
            fi
        done
        
        cat >> "$artifact_dir/maven-metadata.xml" << EOF
    </versions>
    <lastUpdated>$(date +%Y%m%d%H%M%S)</lastUpdated>
  </versioning>
</metadata>
EOF
        
        print_info "  Created metadata for $artifact_name"
    fi
done

print_success "Metadata updated"
echo ""

# Step 5: Commit and push to GitHub
print_info "Step 5/5: Pushing to GitHub Pages..."

git add .
if git diff --staged --quiet; then
    print_warning "No changes to commit (version $VERSION may already be published)"
else
    git commit -m "Release v$VERSION - UIRouter for KCM

Artifacts included:
- Android (AAR)
- iOS arm64 (KLIB)
- iOS x64 (KLIB)
- iOS Simulator arm64 (KLIB)
- Kotlin Multiplatform metadata

Published: $(date '+%Y-%m-%d %H:%M:%S')"

    git push origin main

    if [ $? -eq 0 ]; then
        print_success "Successfully pushed to GitHub Pages"
    else
        print_error "Failed to push to GitHub"
        exit 1
    fi
fi

echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
print_success "Deployment completed successfully!"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
print_info "Version: ${GREEN}$VERSION${NC}"
print_info "Repository: ${BLUE}https://istsest.github.io/uirouter-kcm-maven${NC}"
echo ""
print_info "Users can now add this dependency:"
echo ""
echo "  ${YELLOW}// settings.gradle.kts${NC}"
echo "  repositories {"
echo "      maven { url = uri(\"${BLUE}https://istsest.github.io/uirouter-kcm-maven${NC}\") }"
echo "  }"
echo ""
echo "  ${YELLOW}// build.gradle.kts${NC}"
echo "  dependencies {"
echo "      implementation(\"${GREEN}io.github.istsest:uirouter-for-kcm:$VERSION${NC}\")"
echo "  }"
echo ""
print_warning "Note: GitHub Pages may take 1-2 minutes to update"
echo ""
