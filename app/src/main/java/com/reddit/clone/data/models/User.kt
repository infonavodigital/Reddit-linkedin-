package com.reddit.clone.data.models

data class User(
    val id: String,
    val username: String,
    val avatarUrl: String? = null,
    val karma: Int = 0,
    val cakeDay: String = "", // Account creation date
    val isVerified: Boolean = false,
    val bio: String = ""
)
