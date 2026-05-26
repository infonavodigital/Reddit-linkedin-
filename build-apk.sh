#!/bin/bash

# Reddit Clone - APK Build Script
# This script builds Debug and Release APKs locally

set -e

echo "========================================="
echo "  Reddit Clone - APK Build Script"
echo "========================================="
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Make gradlew executable
echo "Making gradlew executable..."
chmod +x ./gradlew

# Clean previous builds
echo ""
echo "${YELLOW}Cleaning previous builds...${NC}"
./gradlew clean

# Build Debug APK
echo ""
echo "${YELLOW}Building Debug APK...${NC}"
./gradlew assembleDebug --stacktrace

if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    echo "${GREEN}✓ Debug APK built successfully!${NC}"
    echo "  Location: app/build/outputs/apk/debug/app-debug.apk"
    echo "  Size: $(du -h app/build/outputs/apk/debug/app-debug.apk | cut -f1)"
else
    echo "${RED}✗ Debug APK build failed!${NC}"
    exit 1
fi

# Build Release APK
echo ""
echo "${YELLOW}Building Release APK...${NC}"
./gradlew assembleRelease --stacktrace

if [ -f "app/build/outputs/apk/release/app-release-unsigned.apk" ]; then
    echo "${GREEN}✓ Release APK built successfully!${NC}"
    echo "  Location: app/build/outputs/apk/release/app-release-unsigned.apk"
    echo "  Size: $(du -h app/build/outputs/apk/release/app-release-unsigned.apk | cut -f1)"
else
    echo "${RED}✗ Release APK build failed!${NC}"
    exit 1
fi

# Summary
echo ""
echo "========================================="
echo "${GREEN}Build Complete!${NC}"
echo "========================================="
echo ""
echo "📱 APK Files:"
echo "  • Debug:   app/build/outputs/apk/debug/app-debug.apk"
echo "  • Release: app/build/outputs/apk/release/app-release-unsigned.apk"
echo ""
echo "📦 Install with ADB:"
echo "  adb install app/build/outputs/apk/debug/app-debug.apk"
echo ""
