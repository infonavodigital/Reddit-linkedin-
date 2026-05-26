# 🔧 Troubleshooting Guide - Reddit Clone

## Common Build Issues and Solutions

---

## 🚨 GitHub Actions Build Failures

### Issue 1: "upload-artifact deprecated" Warning
**Error Message:**
```
Warning: actions/upload-artifact@v3 is deprecated
```

**Status:** ✅ FIXED
- Updated workflows to use compatible artifact versions
- Simplified workflow logic
- Removed complex conditional statements

**Solution:** Already applied in latest commit. Just push/pull latest code.

---

### Issue 2: Gradle Build Failed
**Error Message:**
```
Task :app:compileDebugKotlin FAILED
```

**Solutions:**

#### A. Check Gradle Wrapper
```bash
# Verify gradle-wrapper.jar exists and is correct size
ls -lh gradle/wrapper/gradle-wrapper.jar
# Should be ~62KB

# Re-download if needed
curl -L -o gradle/wrapper/gradle-wrapper.jar \
  https://raw.githubusercontent.com/gradle/gradle/v8.2.0/gradle/wrapper/gradle-wrapper.jar
```

#### B. Make gradlew Executable
```bash
chmod +x gradlew
git add gradlew
git commit -m "fix: Make gradlew executable"
git push
```

#### C. Clean and Rebuild
```bash
./gradlew clean
./gradlew build --stacktrace
```

---

### Issue 3: "permission denied: ./gradlew"
**Solution:**
```bash
chmod +x gradlew
```

Add to workflow (already included):
```yaml
- name: Grant execute permission for gradlew
  run: chmod +x gradlew
```

---

### Issue 4: No APK Generated
**Check:**
1. Build completed successfully?
2. APK path correct?
   - Debug: `app/build/outputs/apk/debug/app-debug.apk`
   - Release: `app/build/outputs/apk/release/app-release-unsigned.apk`

**Solution:**
```bash
# List all APKs
find . -name "*.apk" -type f

# Build with verbose output
./gradlew assembleDebug --info --stacktrace
```

---

## 💻 Local Build Issues

### Issue 1: "JAVA_HOME not set"
**Error:**
```
ERROR: JAVA_HOME is not set
```

**Solutions:**

**Linux/Mac:**
```bash
# Find Java
which java
/usr/bin/java -version

# Set JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
# Or for Mac:
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home

# Add to ~/.bashrc or ~/.zshrc
echo 'export JAVA_HOME=/path/to/jdk' >> ~/.bashrc
```

**Windows:**
```cmd
# Set JAVA_HOME
set JAVA_HOME=C:\Program Files\Java\jdk-17

# Or permanently in System Environment Variables
setx JAVA_HOME "C:\Program Files\Java\jdk-17"
```

---

### Issue 2: "SDK location not found"
**Error:**
```
SDK location not found
```

**Solution:**

Create `local.properties`:

**Linux:**
```properties
sdk.dir=/home/YOUR_USERNAME/Android/Sdk
```

**Mac:**
```properties
sdk.dir=/Users/YOUR_USERNAME/Library/Android/sdk
```

**Windows:**
```properties
sdk.dir=C:\\Users\\YOUR_USERNAME\\AppData\\Local\\Android\\Sdk
```

Or let Android Studio create it automatically.

---

### Issue 3: "Out of Memory" Error
**Error:**
```
Expiring Daemon because JVM heap space is exhausted
```

**Solution:**

Edit `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx4096m -XX:MaxPermSize=1024m
org.gradle.parallel=true
org.gradle.daemon=true
```

---

### Issue 4: Build Takes Too Long
**Solutions:**

**1. Increase Memory:**
```properties
# In gradle.properties
org.gradle.jvmargs=-Xmx4096m
```

**2. Enable Parallel Builds:**
```properties
org.gradle.parallel=true
org.gradle.caching=true
```

**3. Use Gradle Daemon:**
```properties
org.gradle.daemon=true
```

**4. Clear Cache:**
```bash
rm -rf ~/.gradle/caches/
./gradlew clean build
```

---

## 📱 Installation Issues

### Issue 1: "App not installed"
**Reasons:**
- App already installed with different signature
- Insufficient storage
- Corrupted APK

**Solutions:**
```bash
# Uninstall existing app first
adb uninstall com.reddit.clone

# Then install
adb install app-debug.apk

# Or force reinstall
adb install -r app-debug.apk
```

---

### Issue 2: "Unknown Sources" Required
**Solution:**
1. Go to **Settings** → **Security**
2. Enable **"Unknown Sources"** or **"Install from Unknown Sources"**
3. Or for newer Android: Settings → Apps → Special Access → Install Unknown Apps → Enable for your file manager

---

### Issue 3: "Package installer has stopped"
**Solutions:**
1. Clear cache of Package Installer app
2. Reboot device
3. Use ADB instead:
```bash
adb install -r app-debug.apk
```

---

## 🔍 Debugging Tips

### Enable Verbose Logging
```bash
./gradlew assembleDebug --info --stacktrace
```

### Check Gradle Version
```bash
./gradlew --version
```

### List All Tasks
```bash
./gradlew tasks --all
```

### Check Dependencies
```bash
./gradlew dependencies
```

### View Build Configuration
```bash
./gradlew properties
```

---

## 🐛 Common Errors Reference

### Error: "Execution failed for task ':app:mergeDebugResources'"
**Solution:**
```bash
./gradlew clean
rm -rf app/build
./gradlew assembleDebug
```

### Error: "Could not resolve all dependencies"
**Solution:**
```bash
./gradlew build --refresh-dependencies
```

### Error: "Unable to start the daemon process"
**Solution:**
```bash
# Kill existing Gradle daemons
./gradlew --stop

# Clear cache
rm -rf ~/.gradle/caches/

# Rebuild
./gradlew clean build
```

### Error: "Compilation failed; see the compiler error output for details"
**Solution:**
Check for:
- Kotlin syntax errors
- Missing imports
- Incompatible library versions
- Incorrect package names

Run with stacktrace:
```bash
./gradlew assembleDebug --stacktrace
```

---

## 📊 Health Check Commands

### Verify Setup
```bash
# 1. Check Java
java -version
# Should show: openjdk version "17.x.x"

# 2. Check Gradle wrapper
ls -lh gradlew gradle/wrapper/gradle-wrapper.jar
# gradlew should be executable
# gradle-wrapper.jar should be ~62KB

# 3. Check Android SDK (if local)
echo $ANDROID_HOME
# Should show path to Android SDK

# 4. Test Gradle
./gradlew --version
# Should show Gradle 8.2

# 5. Check dependencies
./gradlew dependencies | head -20
```

### Quick Test Build
```bash
# Clean build
./gradlew clean

# Build debug (fastest)
./gradlew assembleDebug

# Check output
ls -lh app/build/outputs/apk/debug/
```

---

## 🚀 Quick Fixes for Common Scenarios

### Scenario 1: Fresh Clone Not Building
```bash
git clone https://github.com/infonavodigital/Reddit-linkedin-.git
cd Reddit-linkedin-
chmod +x gradlew
./gradlew assembleDebug --stacktrace
```

### Scenario 2: Build Worked Before, Now Fails
```bash
./gradlew clean
rm -rf ~/.gradle/caches/
./gradlew build --refresh-dependencies
```

### Scenario 3: GitHub Actions Keeps Failing
1. Check workflow file syntax: `.github/workflows/build-apk.yml`
2. Verify gradle-wrapper.jar is committed (62KB)
3. Ensure gradlew has executable flag:
```bash
git ls-files -s gradlew
# Should show: 100755 (executable)
```
4. Re-commit if needed:
```bash
chmod +x gradlew
git add gradlew
git commit -m "fix: Make gradlew executable"
git push
```

### Scenario 4: APK Builds But Crashes on Device
**Check logs:**
```bash
adb logcat | grep reddit.clone
```

**Common causes:**
- Missing permissions in AndroidManifest.xml
- API level incompatibility
- ProGuard issues (release builds)

---

## 📞 Still Having Issues?

### 1. Check Existing Issues
https://github.com/infonavodigital/Reddit-linkedin-/issues

### 2. Create New Issue
Include:
- Error message (full stack trace)
- Build command used
- Operating system
- Java version: `java -version`
- Gradle version: `./gradlew --version`
- Steps to reproduce

### 3. Quick Reference
- **APK Build Guide:** `APK_BUILD_GUIDE.md`
- **Build Instructions:** `BUILD_INSTRUCTIONS.md`
- **Deployment Guide:** `DEPLOYMENT.md`
- **Quick Start:** `QUICK_START.md`

---

## ✅ Build Health Checklist

Before reporting issues, verify:

- [ ] Java 17+ installed: `java -version`
- [ ] Gradle wrapper exists: `ls gradlew`
- [ ] gradlew is executable: `chmod +x gradlew`
- [ ] gradle-wrapper.jar exists (62KB)
- [ ] Clean build attempted: `./gradlew clean build`
- [ ] Cache cleared: `rm -rf ~/.gradle/caches/`
- [ ] Latest code pulled: `git pull`
- [ ] No local modifications conflicting: `git status`

---

**💡 Tip:** Most issues are resolved by:
1. `chmod +x gradlew`
2. `./gradlew clean`
3. `./gradlew build --refresh-dependencies`

**🎯 For GitHub Actions issues:** Pull latest code, the workflows are now fixed!
