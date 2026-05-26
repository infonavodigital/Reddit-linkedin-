package com.reddit.clone.data.models

data class Post(
    val id: String,
    val title: String,
    val author: User,
    val subreddit: Subreddit,
    val type: PostType = PostType.TEXT,
    val content: String = "", // Text content
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val linkUrl: String? = null,
    val thumbnailUrl: String? = null,
    val upvotes: Int = 0,
    val downvotes: Int = 0,
    val commentCount: Int = 0,
    val awardCount: Int = 0,
    val timePosted: String = "", // e.g., "2h ago"
    val isUpvoted: Boolean = false,
    val isDownvoted: Boolean = false,
    val isSaved: Boolean = false,
    val flair: String? = null
) {
    val score: Int
        get() = upvotes - downvotes
}

enum class PostType {
    TEXT,
    IMAGE,
    VIDEO,
    LINK,
    POLL
}
