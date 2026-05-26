# Deployment & CI/CD Guide

## 🚀 Automated Build System

Your Reddit Clone now has a complete CI/CD pipeline configured with GitHub Actions!

---

## GitHub Actions Workflows

### 1. Android CI/CD Workflow
**File:** `.github/workflows/android-build.yml`

**Triggers:**
- Push to `main` branch
- Push to `develop` branch
- Pull requests to `main` or `develop`
- Manual trigger (workflow_dispatch)

**What It Does:**
- ✅ Checks out code
- ✅ Sets up JDK 17
- ✅ Caches Gradle dependencies
- ✅ Builds the project
- ✅ Runs lint checks
- ✅ Builds Debug APK
- ✅ Builds Release APK
- ✅ Runs unit tests
- ✅ Uploads APK artifacts
- ✅ Uploads lint results
- ✅ Uploads test results

**View Workflow:**
```
https://github.com/infonavodigital/Reddit-linkedin-/actions
```

---

### 2. Release Workflow
**File:** `.github/workflows/release.yml`

**Triggers:**
- Push of version tags (e.g., `v1.0.0`, `v1.1.0`)
- Manual trigger

**What It Does:**
- ✅ Builds Release APK
- ✅ Signs APK (if secrets configured)
- ✅ Creates GitHub Release
- ✅ Attaches APK to release

**Create a Release:**
```bash
# Tag your commit
git tag v1.0.0
git push origin v1.0.0

# GitHub Actions will automatically:
# 1. Build the release APK
# 2. Create a GitHub release
# 3. Upload the APK
```

---

## Download Built APKs

### From GitHub Actions

1. **Navigate to Actions Tab:**
   ```
   https://github.com/infonavodigital/Reddit-linkedin-/actions
   ```

2. **Click on Latest Workflow Run**

3. **Scroll to Artifacts Section**

4. **Download Available Artifacts:**
   - `app-debug` - Debug APK for testing
   - `app-release` - Release APK (unsigned)
   - `lint-results` - Code quality report
   - `test-results` - Unit test results

### From Releases

1. **Navigate to Releases:**
   ```
   https://github.com/infonavodigital/Reddit-linkedin-/releases
   ```

2. **Download the APK from the latest release**

---

## Manual Workflow Trigger

### Via GitHub UI

1. Go to **Actions** tab
2. Select **Android CI/CD** workflow
3. Click **Run workflow** dropdown
4. Select branch
5. Click **Run workflow** button

### Via GitHub CLI

```bash
# Install GitHub CLI
brew install gh  # macOS
# or download from: https://cli.github.com/

# Authenticate
gh auth login

# Trigger workflow
gh workflow run "Android CI/CD" --ref main
```

---

## Build Status Badge

Add this to your README.md to show build status:

```markdown
![Android CI](https://github.com/infonavodigital/Reddit-linkedin-/workflows/Android%20CI/CD/badge.svg)
```

Result:
![Android CI](https://github.com/infonavodigital/Reddit-linkedin-/workflows/Android%20CI/CD/badge.svg)

---

## Setting Up APK Signing (Optional)

For production releases, you'll want to sign your APKs.

### Step 1: Generate Keystore

```bash
keytool -genkey -v -keystore reddit-clone.keystore \
  -alias reddit-key \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000

# Follow the prompts to set:
# - Keystore password
# - Key password
# - Your information (name, organization, etc.)
```

### Step 2: Encode Keystore to Base64

```bash
# Linux/Mac
base64 -i reddit-clone.keystore -o keystore.base64

# Windows (PowerShell)
[Convert]::ToBase64String([IO.File]::ReadAllBytes("reddit-clone.keystore")) | Out-File keystore.base64
```

### Step 3: Add GitHub Secrets

1. Go to repository **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Add the following secrets:

| Secret Name | Description | Example |
|-------------|-------------|---------|
| `SIGNING_KEY` | Base64 encoded keystore | Contents of keystore.base64 |
| `ALIAS` | Keystore alias | reddit-key |
| `KEY_STORE_PASSWORD` | Keystore password | your_keystore_password |
| `KEY_PASSWORD` | Key password | your_key_password |

### Step 4: Signed Builds

Once secrets are configured, the Release workflow will automatically sign APKs!

---

## Local Build Commands

### Build Debug APK
```bash
./gradlew assembleDebug

# Output: app/build/outputs/apk/debug/app-debug.apk
```

### Build Release APK
```bash
./gradlew assembleRelease

# Output: app/build/outputs/apk/release/app-release-unsigned.apk
```

### Clean Build
```bash
./gradlew clean build
```

### Run All Checks
```bash
./gradlew check
```

---

## Installing APKs

### Via ADB (Android Debug Bridge)

```bash
# Connect device and enable USB debugging

# Check device is connected
adb devices

# Install Debug APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Install Release APK
adb install app/build/outputs/apk/release/app-release-unsigned.apk

# Install with -r to replace existing app
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Via File Transfer

1. Download APK from GitHub Actions or build locally
2. Transfer APK to Android device (email, cloud storage, etc.)
3. On device, go to **Settings** → **Security**
4. Enable **Install from Unknown Sources**
5. Use file manager to locate and tap the APK
6. Follow installation prompts

---

## Monitoring Builds

### Check Build Status

```bash
# Using GitHub CLI
gh run list --workflow="Android CI/CD"

# View specific run
gh run view <run-id>

# Watch live logs
gh run watch
```

### Email Notifications

GitHub automatically sends notifications for:
- Failed builds
- Successful builds (if configured)
- Pull request checks

Configure in: **Settings** → **Notifications**

---

## Branch Protection (Recommended)

Protect your main branch by requiring successful builds:

1. Go to **Settings** → **Branches**
2. Click **Add rule** for `main` branch
3. Enable:
   - ✅ Require status checks to pass
   - ✅ Require branches to be up to date
   - ✅ Status checks: `build`, `test`
4. Save changes

Now merges to `main` require passing CI checks!

---

## Troubleshooting CI/CD

### Build Fails on GitHub Actions

**Check logs:**
1. Go to Actions tab
2. Click the failed run
3. Click the failed job
4. Expand failed steps to see errors

**Common issues:**
- Gradle dependency resolution: Use `--refresh-dependencies`
- Memory issues: Increase heap size in `gradle.properties`
- Network timeouts: Retry the workflow

### Workflow Not Triggering

**Verify:**
- Workflow file is in `.github/workflows/`
- Workflow file has valid YAML syntax
- Branch name matches trigger condition
- Actions are enabled in repository settings

### Artifacts Not Uploading

**Check:**
- Build completed successfully
- APK paths in workflow match actual output
- Artifacts retention period (default 90 days)

---

## Deployment Checklist

Before each release:

- [ ] Update version in `app/build.gradle.kts`
- [ ] Update `CHANGELOG.md` with changes
- [ ] Run tests locally: `./gradlew test`
- [ ] Run lint: `./gradlew lint`
- [ ] Build locally: `./gradlew build`
- [ ] Test on multiple devices/emulators
- [ ] Test both dark and light themes
- [ ] Commit all changes
- [ ] Create version tag: `git tag v1.x.x`
- [ ] Push tag: `git push origin v1.x.x`
- [ ] Verify GitHub Actions build passes
- [ ] Download and test built APK
- [ ] Write release notes on GitHub
- [ ] Announce release if applicable

---

## Production Deployment (Google Play)

When ready for Play Store:

### 1. Prepare App Bundle
```bash
./gradlew bundleRelease
# Output: app/build/outputs/bundle/release/app-release.aab
```

### 2. Sign Bundle
Use Play App Signing or sign manually with jarsigner

### 3. Upload to Play Console
1. Go to https://play.google.com/console
2. Create app listing
3. Upload signed AAB
4. Complete store listing
5. Submit for review

### 4. Automate with fastlane (Advanced)
Install fastlane and configure for automated deployments

---

## Continuous Deployment

For automatic Play Store deployments:

1. **Install Fastlane:**
   ```bash
   gem install fastlane
   ```

2. **Configure Fastlane:**
   ```bash
   fastlane init
   ```

3. **Add to GitHub Actions:**
   - Store Play Store API credentials as secrets
   - Add fastlane deployment step to workflow

4. **Automatic Releases:**
   - Tag commits trigger builds
   - Successful builds deploy to Play Store
   - Beta or production tracks configurable

---

## Repository Status

✅ **Complete CI/CD Setup**
- Automated builds on push
- Lint and test execution
- Artifact generation
- Release automation
- Multi-platform support (Linux/Mac/Windows)

✅ **Documentation**
- Build instructions
- Contributing guidelines
- Issue/PR templates
- Changelog tracking

✅ **Ready for Production**
- All files committed
- Workflows configured
- Build scripts executable
- Dependencies cached

---

## Quick Links

- **Repository:** https://github.com/infonavodigital/Reddit-linkedin-
- **Actions:** https://github.com/infonavodigital/Reddit-linkedin-/actions
- **Releases:** https://github.com/infonavodigital/Reddit-linkedin-/releases
- **Issues:** https://github.com/infonavodigital/Reddit-linkedin-/issues

---

**Your Android app is now fully automated! 🎉**

Every push builds automatically, and releases are one tag away!
