# 📱 APK Build Guide - Reddit Clone

Complete guide to building APK files for your Reddit Clone Android app.

---

## 🚀 Quick Start - 3 Methods

### Method 1: GitHub Actions (Automatic) ⭐ RECOMMENDED

**Zero setup required! APKs are built automatically.**

#### Automatic Builds
Every time you push code to `main`, `develop`, or `master` branch:
1. GitHub Actions automatically builds APKs
2. Wait 5-10 minutes for completion
3. Download from Artifacts

#### Manual Build Trigger
1. Go to: https://github.com/infonavodigital/Reddit-linkedin-/actions
2. Click **"Build APK"** workflow
3. Click **"Run workflow"** button
4. Select branch and build type (debug/release/both)
5. Click green **"Run workflow"**
6. Wait for completion (~5-10 minutes)

#### Download Built APKs
1. Go to: https://github.com/infonavodigital/Reddit-linkedin-/actions
2. Click on the latest **green** workflow run
3. Scroll down to **"Artifacts"** section
4. Download:
   - **reddit-clone-debug** - For testing
   - **reddit-clone-release** - For production

---

### Method 2: Local Build with Scripts (Easy)

#### On Linux/Mac:
```bash
# Clone repository
git clone https://github.com/infonavodigital/Reddit-linkedin-.git
cd Reddit-linkedin-

# Run build script
./build-apk.sh

# APKs will be in:
# app/build/outputs/apk/debug/app-debug.apk
# app/build/outputs/apk/release/app-release-unsigned.apk
```

#### On Windows:
```cmd
# Clone repository
git clone https://github.com/infonavodigital/Reddit-linkedin-.git
cd Reddit-linkedin-

# Run build script
build-apk.bat

# APKs will be in:
# app\build\outputs\apk\debug\app-debug.apk
# app\build\outputs\apk\release\app-release-unsigned.apk
```

---

### Method 3: Manual Gradle Build

#### Prerequisites
- JDK 17 or higher installed
- Android SDK installed (or use Android Studio)

#### Build Debug APK
```bash
# Make gradlew executable (Linux/Mac only)
chmod +x gradlew

# Build Debug APK
./gradlew assembleDebug

# On Windows use:
gradlew.bat assembleDebug

# Output: app/build/outputs/apk/debug/app-debug.apk
```

#### Build Release APK
```bash
# Build Release APK
./gradlew assembleRelease

# On Windows:
gradlew.bat assembleRelease

# Output: app/build/outputs/apk/release/app-release-unsigned.apk
```

#### Build Both
```bash
./gradlew assembleDebug assembleRelease

# Or simply:
./gradlew build
```

---

## 📦 Understanding APK Types

### Debug APK
- **File:** `app-debug.apk`
- **Purpose:** Development and testing
- **Size:** Larger (~10-20MB)
- **Features:**
  - Debugging enabled
  - Logs included
  - Not optimized
  - Can install alongside release version
- **Use for:** Testing on devices, development

### Release APK (Unsigned)
- **File:** `app-release-unsigned.apk`
- **Purpose:** Production deployment
- **Size:** Smaller (~8-15MB)
- **Features:**
  - Optimized and minified
  - ProGuard/R8 applied
  - Debugging disabled
  - Requires signing for Play Store
- **Use for:** Final testing, production

---

## 🔧 GitHub Actions Workflows

### Workflow 1: Build APK (Primary)
**File:** `.github/workflows/build-apk.yml`

**Features:**
- ✅ Builds Debug and Release APKs
- ✅ Manual trigger with options
- ✅ Uploads artifacts automatically
- ✅ Shows build summary
- ✅ APK size information

**Triggers:**
- Push to main/develop/master
- Pull requests
- Manual trigger (workflow_dispatch)

### Workflow 2: Android CI/CD (Full Testing)
**File:** `.github/workflows/android-build.yml`

**Features:**
- ✅ Complete build process
- ✅ Lint checks
- ✅ Unit tests
- ✅ APK generation
- ✅ Test reports

**Triggers:**
- Push to main/develop
- Pull requests
- Manual trigger

---

## 📥 Installing APKs

### On Android Device

#### Method 1: Direct Transfer
1. Download APK from GitHub Actions or build locally
2. Transfer to device (USB, email, cloud storage)
3. On device: **Settings** → **Security** → Enable **"Install from Unknown Sources"**
4. Use file manager to find APK
5. Tap to install

#### Method 2: Using ADB (Recommended)
```bash
# Connect device via USB
# Enable USB Debugging in Developer Options

# Verify device is connected
adb devices

# Install Debug APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Install Release APK
adb install app/build/outputs/apk/release/app-release-unsigned.apk

# Force reinstall (replace existing)
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Uninstall if needed
adb uninstall com.reddit.clone
```

---

## 🎯 Build Troubleshooting

### Error: "gradlew: Permission denied"
**Solution (Linux/Mac):**
```bash
chmod +x gradlew
```

### Error: "JAVA_HOME not set"
**Solution:**
```bash
# Find Java path
which java

# Set JAVA_HOME (Linux/Mac)
export JAVA_HOME=/path/to/jdk

# Set JAVA_HOME (Windows)
set JAVA_HOME=C:\Program Files\Java\jdk-17
```

### Error: "SDK location not found"
**Solution:**
```bash
# Create local.properties file
echo "sdk.dir=/path/to/android-sdk" > local.properties

# On Mac:
echo "sdk.dir=/Users/YOUR_USERNAME/Library/Android/sdk" > local.properties

# On Linux:
echo "sdk.dir=/home/YOUR_USERNAME/Android/Sdk" > local.properties

# On Windows:
echo sdk.dir=C:\\Users\\YOUR_USERNAME\\AppData\\Local\\Android\\Sdk > local.properties
```

### Error: "Build failed with Gradle"
**Solutions:**
```bash
# Clean and rebuild
./gradlew clean build

# Clear Gradle cache
rm -rf ~/.gradle/caches/
./gradlew build --refresh-dependencies

# Increase memory
export GRADLE_OPTS="-Xmx4096m -XX:MaxPermSize=1024m"
./gradlew build
```

### GitHub Actions Build Fails
**Check:**
1. Go to Actions tab
2. Click failed workflow
3. Expand failed steps
4. Read error messages
5. Check if gradlew is executable
6. Verify gradle-wrapper.jar exists

---

## 🔐 Signing APKs (For Production)

### Generate Keystore
```bash
keytool -genkey -v -keystore reddit-clone-key.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias reddit-clone
```

### Sign APK Manually
```bash
# Sign the APK
jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
  -keystore reddit-clone-key.jks \
  app/build/outputs/apk/release/app-release-unsigned.apk \
  reddit-clone

# Verify signature
jarsigner -verify -verbose \
  app/build/outputs/apk/release/app-release-unsigned.apk

# Optimize (align) APK
zipalign -v 4 \
  app/build/outputs/apk/release/app-release-unsigned.apk \
  reddit-clone-release-signed.apk
```

### Configure in Build
Add to `app/build.gradle.kts`:
```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("../reddit-clone-key.jks")
            storePassword = "your_keystore_password"
            keyAlias = "reddit-clone"
            keyPassword = "your_key_password"
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

---

## 📊 APK Information

### Check APK Details
```bash
# Get APK info using aapt
aapt dump badging app/build/outputs/apk/debug/app-debug.apk

# Get APK size
du -h app/build/outputs/apk/debug/app-debug.apk

# List contents
unzip -l app/build/outputs/apk/debug/app-debug.apk
```

### Expected APK Sizes
- **Debug APK:** 10-20 MB
- **Release APK:** 8-15 MB

---

## 🚀 Quick Commands Cheat Sheet

```bash
# Build Debug APK
./gradlew assembleDebug

# Build Release APK
./gradlew assembleRelease

# Build both
./gradlew build

# Clean build
./gradlew clean build

# Install Debug APK via ADB
adb install app/build/outputs/apk/debug/app-debug.apk

# Uninstall app
adb uninstall com.reddit.clone

# View build logs
./gradlew assembleDebug --info

# View all tasks
./gradlew tasks --all
```

---

## 📱 Testing APK

### Before Installing
- ✅ Check APK size (should be 8-20MB)
- ✅ Verify APK was built successfully
- ✅ Check build logs for errors

### After Installing
- ✅ Open app successfully
- ✅ Test home feed scrolling
- ✅ Test upvote/downvote
- ✅ Open post details
- ✅ View comments
- ✅ Test search
- ✅ Navigate all tabs
- ✅ Test dark/light mode

---

## 🎓 Additional Resources

- **Android Studio:** https://developer.android.com/studio
- **Gradle Docs:** https://docs.gradle.org/
- **APK Signing:** https://developer.android.com/studio/publish/app-signing
- **ADB Commands:** https://developer.android.com/studio/command-line/adb

---

## ✅ Build Checklist

Before building for production:

- [ ] Update version in `app/build.gradle.kts`
- [ ] Update `CHANGELOG.md`
- [ ] Test all features
- [ ] Run lint: `./gradlew lint`
- [ ] Run tests: `./gradlew test`
- [ ] Build release APK
- [ ] Sign APK with production keystore
- [ ] Test signed APK on device
- [ ] Verify app permissions
- [ ] Check APK size
- [ ] Test on different Android versions

---

## 📞 Support

**Issues with building?**
1. Check this guide thoroughly
2. Check BUILD_INSTRUCTIONS.md
3. Check DEPLOYMENT.md
4. Create issue on GitHub: https://github.com/infonavodigital/Reddit-linkedin-/issues

---

**🎉 Happy Building! Your Reddit Clone APK is ready to install! 📱**

**Download APKs from GitHub Actions:** https://github.com/infonavodigital/Reddit-linkedin-/actions
