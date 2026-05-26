package com.reddit.clone.data

import com.reddit.clone.data.models.*

object SampleData {
    
    // Sample Users
    val sampleUsers = listOf(
        User(
            id = "1",
            username = "u/AndroidDev",
            karma = 12453,
            cakeDay = "Jan 15, 2020",
            isVerified = true
        ),
        User(
            id = "2",
            username = "u/TechEnthusiast",
            karma = 8932,
            cakeDay = "Mar 22, 2021"
        ),
        User(
            id = "3",
            username = "u/CodeMaster",
            karma = 24156,
            cakeDay = "Aug 10, 2019",
            isVerified = true
        )
    )
    
    // Sample Subreddits
    val sampleSubreddits = listOf(
        Subreddit(
            id = "1",
            name = "r/androiddev",
            displayName = "Android Development",
            description = "News for Android developers with the who, what, where when and how of the Android community.",
            memberCount = 285000,
            onlineCount = 1250,
            isJoined = true
        ),
        Subreddit(
            id = "2",
            name = "r/programming",
            displayName = "Programming",
            description = "Computer Programming",
            memberCount = 6200000,
            onlineCount = 12000,
            isJoined = true
        ),
        Subreddit(
            id = "3",
            name = "r/kotlin",
            displayName = "Kotlin Programming Language",
            description = "Discussion about Kotlin, a statically typed programming language for the JVM, Android, JavaScript, and native.",
            memberCount = 95000,
            onlineCount = 450,
            isJoined = false
        ),
        Subreddit(
            id = "4",
            name = "r/jetpackcompose",
            displayName = "Jetpack Compose",
            description = "Jetpack Compose is Android's modern toolkit for building native UI.",
            memberCount = 18000,
            onlineCount = 120,
            isJoined = true
        ),
        Subreddit(
            id = "5",
            name = "r/technology",
            displayName = "Technology",
            description = "Subreddit dedicated to the news and discussions about the creation and use of technology.",
            memberCount = 14000000,
            onlineCount = 25000,
            isJoined = false
        )
    )
    
    // Sample Comments
    fun getSampleComments(postId: String): List<Comment> {
        return listOf(
            Comment(
                id = "c1",
                author = sampleUsers[1],
                content = "This is a great feature! I've been waiting for this for a long time.",
                upvotes = 245,
                timePosted = "2h ago",
                isOP = false,
                replies = listOf(
                    Comment(
                        id = "c2",
                        author = sampleUsers[0],
                        content = "Glad you like it! We worked hard on this.",
                        upvotes = 128,
                        timePosted = "1h ago",
                        depth = 1,
                        isOP = true
                    ),
                    Comment(
                        id = "c3",
                        author = sampleUsers[2],
                        content = "Same here! The API is very intuitive.",
                        upvotes = 67,
                        timePosted = "1h ago",
                        depth = 1
                    )
                )
            ),
            Comment(
                id = "c4",
                author = sampleUsers[2],
                content = "Does this support Material 3 design guidelines?",
                upvotes = 89,
                timePosted = "3h ago",
                replies = listOf(
                    Comment(
                        id = "c5",
                        author = sampleUsers[0],
                        content = "Yes, it's fully compatible with Material 3!",
                        upvotes = 112,
                        timePosted = "2h ago",
                        depth = 1,
                        isOP = true
                    )
                )
            ),
            Comment(
                id = "c6",
                author = sampleUsers[1],
                content = "Anyone having issues with performance on older devices?",
                upvotes = 34,
                timePosted = "4h ago"
            )
        )
    }
    
    // Sample Posts
    val samplePosts = listOf(
        Post(
            id = "1",
            title = "Jetpack Compose 1.6 is now stable! 🎉",
            author = sampleUsers[0],
            subreddit = sampleSubreddits[3],
            type = PostType.TEXT,
            content = "The latest version of Jetpack Compose brings amazing new features including improved performance, new animations, and better support for Material 3.",
            upvotes = 2453,
            commentCount = 187,
            awardCount = 12,
            timePosted = "3h ago",
            flair = "News"
        ),
        Post(
            id = "2",
            title = "What are your favorite Android development tools in 2024?",
            author = sampleUsers[1],
            subreddit = sampleSubreddits[0],
            type = PostType.TEXT,
            content = "I'm curious to know what tools everyone is using. Here are mine:\n\n1. Android Studio\n2. Firebase\n3. Retrofit\n4. Room Database\n\nWhat about you?",
            upvotes = 1823,
            commentCount = 342,
            awardCount = 5,
            timePosted = "5h ago",
            flair = "Discussion"
        ),
        Post(
            id = "3",
            title = "Beautiful UI animation using Compose",
            author = sampleUsers[2],
            subreddit = sampleSubreddits[3],
            type = PostType.IMAGE,
            imageUrl = "https://via.placeholder.com/800x600/FF5700/FFFFFF?text=Beautiful+Animation",
            thumbnailUrl = "https://via.placeholder.com/150x150/FF5700/FFFFFF?text=Thumb",
            upvotes = 5621,
            commentCount = 234,
            awardCount = 28,
            timePosted = "8h ago",
            flair = "Showcase"
        ),
        Post(
            id = "4",
            title = "How to implement Clean Architecture in Android",
            author = sampleUsers[0],
            subreddit = sampleSubreddits[0],
            type = PostType.LINK,
            linkUrl = "https://developer.android.com/topic/architecture",
            thumbnailUrl = "https://via.placeholder.com/150x150/0079D3/FFFFFF?text=Article",
            upvotes = 892,
            commentCount = 78,
            awardCount = 3,
            timePosted = "12h ago",
            flair = "Tutorial"
        ),
        Post(
            id = "5",
            title = "Kotlin 2.0 Beta - What's new?",
            author = sampleUsers[1],
            subreddit = sampleSubreddits[2],
            type = PostType.TEXT,
            content = "Kotlin 2.0 brings major improvements:\n\n• K2 compiler is now stable\n• Better performance\n• New language features\n• Improved IDE support",
            upvotes = 3421,
            commentCount = 156,
            awardCount = 15,
            timePosted = "1d ago",
            flair = "News"
        ),
        Post(
            id = "6",
            title = "Share your side projects!",
            author = sampleUsers[2],
            subreddit = sampleSubreddits[1],
            type = PostType.TEXT,
            content = "Let's see what cool things everyone is building. I'll start - I'm working on a recipe app with ML-powered ingredient recognition.",
            upvotes = 1256,
            commentCount = 423,
            awardCount = 7,
            timePosted = "1d ago",
            flair = "Show HN"
        ),
        Post(
            id = "7",
            title = "Android Studio Hedgehog is available",
            author = sampleUsers[0],
            subreddit = sampleSubreddits[0],
            type = PostType.TEXT,
            content = "The new Android Studio version includes Compose Preview improvements, Gemini AI assistant, and better profiling tools.",
            upvotes = 1876,
            commentCount = 92,
            awardCount = 6,
            timePosted = "2d ago",
            flair = "Tools"
        ),
        Post(
            id = "8",
            title = "My first published app! 🚀",
            author = sampleUsers[1],
            subreddit = sampleSubreddits[0],
            type = PostType.IMAGE,
            imageUrl = "https://via.placeholder.com/800x600/24A0ED/FFFFFF?text=My+App+Screenshot",
            thumbnailUrl = "https://via.placeholder.com/150x150/24A0ED/FFFFFF?text=App",
            content = "After 6 months of learning Android development, I finally published my first app! It's a simple to-do app, but I'm proud of it.",
            upvotes = 4532,
            commentCount = 289,
            awardCount = 34,
            timePosted = "2d ago",
            flair = "Milestone"
        )
    )
}
