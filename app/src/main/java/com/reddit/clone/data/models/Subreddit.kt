package com.reddit.clone.data.models

data class Subreddit(
    val id: String,
    val name: String, // e.g., "r/androiddev"
    val displayName: String,
    val description: String,
    val iconUrl: String? = null,
    val bannerUrl: String? = null,
    val memberCount: Int = 0,
    val onlineCount: Int = 0,
    val isJoined: Boolean = false,
    val createdAt: String = ""
)
