package com.reddit.clone.data.models

data class Comment(
    val id: String,
    val author: User,
    val content: String,
    val upvotes: Int = 0,
    val downvotes: Int = 0,
    val timePosted: String = "", // e.g., "1h ago"
    val isUpvoted: Boolean = false,
    val isDownvoted: Boolean = false,
    val awardCount: Int = 0,
    val depth: Int = 0, // Nesting level for threaded comments
    val replies: List<Comment> = emptyList(),
    val isCollapsed: Boolean = false,
    val isOP: Boolean = false // Is original poster
) {
    val score: Int
        get() = upvotes - downvotes
}
