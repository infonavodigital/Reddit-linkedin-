# Build Instructions for Reddit Clone

## Prerequisites

### Required Software
1. **Java Development Kit (JDK)**
   - JDK 17 or higher
   - Download from: https://adoptium.net/

2. **Android Studio**
   - Android Studio Hedgehog (2023.1.1) or newer
   - Download from: https://developer.android.com/studio

3. **Git**
   - For cloning the repository
   - Download from: https://git-scm.com/

### Android SDK Requirements
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)
- Compile SDK: 34

---

## Build Methods

### Method 1: Build with Android Studio (Recommended)

#### Step 1: Clone the Repository
```bash
git clone https://github.com/infonavodigital/Reddit-linkedin-.git
cd Reddit-linkedin-
```

#### Step 2: Open in Android Studio
1. Launch Android Studio
2. Click **File → Open**
3. Navigate to the cloned repository folder
4. Click **OK**
5. Wait for Gradle sync to complete (this may take a few minutes)

#### Step 3: Configure SDK
1. If prompted, install required SDK components
2. Go to **File → Project Structure**
3. Verify SDK location is set correctly

#### Step 4: Build the Project
1. Click **Build → Make Project** (Ctrl+F9 / Cmd+F9)
2. Wait for build to complete
3. Check the Build Output for any errors

#### Step 5: Run on Device/Emulator
1. Connect an Android device or start an emulator
2. Click **Run → Run 'app'** (Shift+F10)
3. Select target device
4. App will install and launch automatically

---

### Method 2: Build from Command Line

#### Step 1: Clone and Navigate
```bash
git clone https://github.com/infonavodigital/Reddit-linkedin-.git
cd Reddit-linkedin-
```

#### Step 2: Make Gradlew Executable (Linux/Mac)
```bash
chmod +x gradlew
```

#### Step 3: Build Debug APK
```bash
./gradlew assembleDebug
```
**Output location:** `app/build/outputs/apk/debug/app-debug.apk`

#### Step 4: Build Release APK (Unsigned)
```bash
./gradlew assembleRelease
```
**Output location:** `app/build/outputs/apk/release/app-release-unsigned.apk`

#### Step 5: Install on Device
```bash
# Make sure device is connected via ADB
adb devices

# Install the APK
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

### Method 3: Build with GitHub Actions (CI/CD)

The project includes automated builds via GitHub Actions.

#### Automatic Builds
Every push to `main` or `develop` branch triggers:
- Gradle build
- Lint checks
- Unit tests
- Debug APK generation
- Release APK generation

#### Manual Build
1. Go to the repository on GitHub
2. Click **Actions** tab
3. Select **Android CI/CD** workflow
4. Click **Run workflow**
5. Wait for build to complete
6. Download artifacts from the workflow run

#### Download Built APKs
After successful workflow run:
1. Click on the completed workflow
2. Scroll to **Artifacts** section
3. Download:
   - `app-debug` - Debug APK
   - `app-release` - Release APK (unsigned)
   - `lint-results` - Lint report

---

## Build Variants

### Debug Build
- Includes debugging information
- Faster build time
- Suitable for development
```bash
./gradlew assembleDebug
```

### Release Build
- Optimized and minified
- Requires signing for production
- Better performance
```bash
./gradlew assembleRelease
```

---

## Common Build Commands

### Clean Build
```bash
./gradlew clean
```

### Build All Variants
```bash
./gradlew build
```

### Run Tests
```bash
./gradlew test
```

### Run Lint
```bash
./gradlew lint
```

### Check Dependencies
```bash
./gradlew dependencies
```

### View All Tasks
```bash
./gradlew tasks
```

---

## Signing the Release APK

### Generate Keystore
```bash
keytool -genkey -v -keystore reddit-clone.keystore -alias reddit-key \
  -keyalg RSA -keysize 2048 -validity 10000
```

### Sign APK
```bash
jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
  -keystore reddit-clone.keystore \
  app/build/outputs/apk/release/app-release-unsigned.apk reddit-key
```

### Verify Signature
```bash
jarsigner -verify -verbose -certs \
  app/build/outputs/apk/release/app-release-unsigned.apk
```

### Align APK (Optional but Recommended)
```bash
zipalign -v 4 app/build/outputs/apk/release/app-release-unsigned.apk \
  reddit-clone-release.apk
```

---

## Troubleshooting

### Issue: Gradle Sync Failed
**Solution:**
- Check internet connection
- Verify JDK is installed correctly
- Run: `./gradlew --refresh-dependencies`

### Issue: SDK Not Found
**Solution:**
- Open Android Studio → Preferences → Appearance & Behavior → System Settings → Android SDK
- Install required SDK platforms and tools
- Update `local.properties` with correct SDK path

### Issue: Build Failed with "Out of Memory"
**Solution:**
Add to `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx4096m -XX:MaxPermSize=1024m
```

### Issue: gradlew: Permission Denied
**Solution:**
```bash
chmod +x gradlew
```

### Issue: Unable to Find Tools.jar
**Solution:**
- Ensure JDK (not JRE) is installed
- Set JAVA_HOME environment variable:
```bash
export JAVA_HOME=/path/to/jdk
```

### Issue: Duplicate Class Error
**Solution:**
```bash
./gradlew clean
./gradlew build --refresh-dependencies
```

---

## Build Configuration Files

### Key Files
- `build.gradle.kts` - Root build configuration
- `app/build.gradle.kts` - App module configuration
- `settings.gradle.kts` - Project settings
- `gradle.properties` - Gradle properties
- `gradle/wrapper/` - Gradle wrapper files

### Dependencies
All dependencies are managed in `app/build.gradle.kts`:
- Jetpack Compose BOM 2023.10.01
- Navigation Compose 2.7.5
- Coil 2.5.0
- Material 3
- Kotlin 1.9.20

---

## Performance Tips

### Faster Builds
1. Enable parallel builds:
```properties
# gradle.properties
org.gradle.parallel=true
org.gradle.caching=true
```

2. Use configuration cache:
```bash
./gradlew build --configuration-cache
```

3. Increase heap size:
```properties
org.gradle.jvmargs=-Xmx4096m
```

### Optimize for Development
- Use debug variant during development
- Enable instant run in Android Studio
- Use emulator with hardware acceleration

---

## CI/CD Pipeline

### GitHub Actions Workflows

**Main Workflow:** `.github/workflows/android-build.yml`
- Triggered on push to main/develop
- Runs lint, tests, and builds
- Uploads APK artifacts

**Release Workflow:** `.github/workflows/release.yml`
- Triggered on version tags (v*)
- Builds signed release APK
- Creates GitHub release

### Setting Up Secrets (for signed builds)
1. Go to repository Settings → Secrets
2. Add the following secrets:
   - `SIGNING_KEY` - Base64 encoded keystore
   - `ALIAS` - Keystore alias
   - `KEY_STORE_PASSWORD` - Keystore password
   - `KEY_PASSWORD` - Key password

---

## System Requirements

### Minimum
- OS: Windows 10, macOS 10.14, Linux (64-bit)
- RAM: 8 GB
- Disk Space: 8 GB free
- Screen Resolution: 1280 x 800

### Recommended
- OS: Latest version
- RAM: 16 GB or more
- Disk Space: 20 GB free (for SDK, emulators)
- Screen Resolution: 1920 x 1080 or higher
- SSD for faster builds

---

## Additional Resources

- [Android Developer Guide](https://developer.android.com/guide)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Gradle User Guide](https://docs.gradle.org/current/userguide/userguide.html)
- [Material Design 3](https://m3.material.io/)

---

## Need Help?

- Check [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines
- Open an issue on GitHub
- Review existing issues for solutions

---

**Happy Building! 🚀**
