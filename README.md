# Reddit Clone - Android App

A fully functional Reddit clone for Android built with modern Android development practices.

## 🎨 Features

### Core Functionality
- **Home Feed** - Scrollable post feed with Best, Hot, New, Top, and Rising filters
- **Post Details** - View full posts with nested, threaded comments
- **Communities** - Browse and join subreddits with tabs for different categories
- **Search** - Search for posts, communities, and people
- **Profile** - User profile with karma stats, posts, comments, and saved items
- **Create Post** - Create text, image, link, and poll posts
- **Chat** - Messaging interface (UI ready)

### UI/UX Features
- **Reddit's Signature Design** - Orange (#FF4500) color scheme matching Reddit's brand
- **Vote System** - Upvote/downvote posts and comments with color-coded feedback
- **Nested Comments** - Threaded comment system with collapse/expand functionality
- **Bottom Navigation** - 5 tabs: Home, Popular, Communities, Chat, Profile
- **Dark/Light Theme** - Automatic theme switching based on system settings
- **Material 3 Design** - Modern Android design guidelines

## 🏗️ Architecture

### Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM-ready structure
- **Navigation**: Jetpack Navigation Compose
- **Image Loading**: Coil
- **Design System**: Material 3

### Project Structure
```
app/src/main/java/com/reddit/clone/
├── data/
│   ├── models/          # Data models (Post, Comment, User, Subreddit)
│   └── SampleData.kt    # Sample data for testing
├── navigation/
│   └── NavGraph.kt      # Navigation setup
├── ui/
│   ├── components/      # Reusable UI components
│   │   ├── PostCard.kt
│   │   ├── CommentItem.kt
│   │   ├── VoteButtons.kt
│   │   ├── SubredditItem.kt
│   │   └── BottomNavigationBar.kt
│   ├── screens/         # Screen composables
│   │   ├── HomeFeedScreen.kt
│   │   ├── PostDetailScreen.kt
│   │   ├── CommunitiesScreen.kt
│   │   ├── SubredditScreen.kt
│   │   ├── ProfileScreen.kt
│   │   ├── SearchScreen.kt
│   │   ├── CreatePostScreen.kt
│   │   ├── PopularScreen.kt
│   │   └── ChatScreen.kt
│   └── theme/           # Theme configuration
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
└── MainActivity.kt
```

## 🎨 Design System

### Colors
- **Primary (Reddit Orange)**: #FF4500
- **Upvote Orange**: #FF8b60
- **Downvote Blue**: #9494FF
- **Background Dark**: #030303
- **Surface Dark**: #1A1A1B
- **Background Light**: #DAE0E6

### Components
1. **PostCard** - Displays post with thumbnail, vote buttons, metadata
2. **CommentItem** - Nested comment with threading and collapse
3. **VoteButtons** - Vertical/horizontal vote UI with score display
4. **SubredditItem** - Community card with member count and join button
5. **BottomNavigationBar** - 5-tab navigation matching Reddit's layout

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or newer
- JDK 8 or higher
- Android SDK 24+ (Android 7.0+)

### Building the App

1. **Clone the repository**
```bash
git clone <repository-url>
cd Reddit-linkedin-
```

2. **Open in Android Studio**
- Open Android Studio
- Select "Open an existing project"
- Navigate to the project directory

3. **Build and Run**
- Click "Run" or press Shift+F10
- Select an emulator or connected device
- Wait for Gradle build to complete

### Building APK
```bash
./gradlew assembleDebug
```
The APK will be in `app/build/outputs/apk/debug/`

### Building Release APK
```bash
./gradlew assembleRelease
```

## 📱 Screens Overview

### 1. Home Feed
- Feed filter tabs (Best, Hot, New, Top, Rising)
- Scrollable post list with PostCard components
- FAB for creating new posts
- Search button in top bar

### 2. Post Detail
- Full post content with images/links
- Vote buttons and action buttons (comment, share, save)
- Nested comment system with threading
- Comment input bar
- Sort options for comments

### 3. Communities
- Tab navigation for different categories
- List of subreddits with join/leave functionality
- Member count and online users
- Search communities

### 4. Profile
- User avatar and username
- Karma and post statistics
- Tabs: Posts, Comments, Saved, About
- Edit profile button
- Cake day display

### 5. Search
- Search bar with clear button
- Tabs for Posts, Communities, People
- Filtered results based on query
- Real-time search

### 6. Create Post
- Subreddit selector
- Post type tabs (Text, Image, Link, Poll)
- Title and content inputs
- Post validation
- Submit button

### 7. Popular
- Posts sorted by popularity (upvotes)
- Filter options
- Same PostCard layout as home feed

### 8. Chat
- Empty state with call-to-action
- Ready for messaging integration

## 🎯 Key Features Implemented

✅ Complete navigation system with bottom bar  
✅ Reddit-style orange color theme (#FF4500)  
✅ Upvote/downvote system with visual feedback  
✅ Nested, threaded comments with collapse  
✅ Post creation with multiple types  
✅ Search functionality across posts and communities  
✅ Community browsing and joining  
✅ User profile with stats  
✅ Dark/Light theme support  
✅ Material 3 design system  
✅ Sample data for testing  

## 🔧 Configuration

### Minimum SDK
- **minSdk**: 24 (Android 7.0)
- **targetSdk**: 34 (Android 14)
- **compileSdk**: 34

### Dependencies
- Jetpack Compose: BOM 2023.10.01
- Navigation Compose: 2.7.5
- Coil: 2.5.0
- Material 3: Latest from Compose BOM
- Kotlin: 1.9.20

## 📝 Future Enhancements

- Backend integration with Reddit API
- User authentication
- Real-time chat functionality
- Image upload for posts
- Video post support
- Award system implementation
- Notification system
- Saved posts persistence
- Comment reply functionality
- Share functionality
- Report/block features

## 📄 License

This is a clone project for educational purposes.

## 👨‍💻 Development

Built with ❤️ using Kotlin and Jetpack Compose

---

**Note**: This app uses sample data for demonstration. To connect to Reddit's actual API, you'll need to register your app at https://www.reddit.com/prefs/apps and implement OAuth authentication.
