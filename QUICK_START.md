# 🚀 Quick Start - Reddit Clone

## 📱 Get Your APK in 3 Steps!

### Option 1: Download Pre-Built APK (Fastest! ⚡)

```
1. Go to: https://github.com/infonavodigital/Reddit-linkedin-/actions
2. Click "Build APK" → Latest green run
3. Download "reddit-clone-debug" from Artifacts
4. Install on your Android device!
```

**⏱️ Time: 2 minutes**

---

### Option 2: Trigger New Build on GitHub

```
1. Go to: https://github.com/infonavodigital/Reddit-linkedin-/actions
2. Click "Build APK" workflow
3. Click "Run workflow" button
4. Select "both" for build type
5. Click green "Run workflow"
6. Wait 5-10 minutes
7. Download from Artifacts
```

**⏱️ Time: 10 minutes**

---

### Option 3: Build Locally

#### Linux/Mac:
```bash
git clone https://github.com/infonavodigital/Reddit-linkedin-.git
cd Reddit-linkedin-
./build-apk.sh
```

#### Windows:
```cmd
git clone https://github.com/infonavodigital/Reddit-linkedin-.git
cd Reddit-linkedin-
build-apk.bat
```

**⏱️ Time: 5-15 minutes (first build)**

**APK Location:** `app/build/outputs/apk/debug/app-debug.apk`

---

## 📥 Install APK on Android

### Method 1: File Transfer
```
1. Transfer APK to phone (USB/Email/Cloud)
2. Settings → Security → Allow Unknown Sources
3. Tap APK file → Install
```

### Method 2: ADB (Recommended)
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## 🎯 What Works Out of the Box

✅ **Home Feed** - Scroll through posts  
✅ **Upvote/Downvote** - Tap arrows to vote  
✅ **Post Details** - Tap post to view full content  
✅ **Comments** - Nested threaded comments  
✅ **Communities** - Browse and join subreddits  
✅ **Search** - Find posts and communities  
✅ **Profile** - View user stats and karma  
✅ **Create Post** - Make text/image/link/poll posts  
✅ **Dark/Light Mode** - Auto switches with system  
✅ **Bottom Nav** - 5 tabs: Home, Popular, Communities, Chat, Profile  

---

## 🔗 Quick Links

| Link | Purpose |
|------|---------|
| [GitHub Actions](https://github.com/infonavodigital/Reddit-linkedin-/actions) | Download APKs |
| [Build APK Workflow](https://github.com/infonavodigital/Reddit-linkedin-/actions/workflows/build-apk.yml) | Trigger new build |
| [Issues](https://github.com/infonavodigital/Reddit-linkedin-/issues) | Report bugs |

---

## 📚 Full Documentation

- **APK_BUILD_GUIDE.md** - Complete APK build guide
- **README.md** - Project overview
- **BUILD_INSTRUCTIONS.md** - Detailed build instructions
- **DEPLOYMENT.md** - CI/CD and deployment
- **FEATURES.md** - Complete feature list

---

## ⚡ Super Quick Command

```bash
# Clone, build, and install in one go (Linux/Mac)
git clone https://github.com/infonavodigital/Reddit-linkedin-.git && \
cd Reddit-linkedin- && \
./build-apk.sh && \
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## 🐛 Troubleshooting

### APK Not Installing
- Enable "Unknown Sources" in Android Settings
- Check if app is already installed (uninstall first)
- Try: `adb install -r app-debug.apk` (force reinstall)

### Build Failed
- Check Java is installed: `java -version` (need JDK 17+)
- Make gradlew executable: `chmod +x gradlew`
- Clean build: `./gradlew clean build`

### GitHub Actions Build Failed
- Check Actions tab for error logs
- Ensure gradle-wrapper.jar exists (should be 62KB)
- Try manual trigger with "Run workflow"

---

## 🎉 You're All Set!

Your Reddit Clone is ready to use. Enjoy exploring the app!

**Need help?** Check the full guides or create an issue on GitHub.
