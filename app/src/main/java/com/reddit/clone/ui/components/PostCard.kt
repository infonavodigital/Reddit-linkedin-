package com.reddit.clone.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.reddit.clone.data.models.Post
import com.reddit.clone.data.models.PostType

@Composable
fun PostCard(
    post: Post,
    onPostClick: (Post) -> Unit,
    onSubredditClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isUpvoted by remember { mutableStateOf(post.isUpvoted) }
    var isDownvoted by remember { mutableStateOf(post.isDownvoted) }
    var isSaved by remember { mutableStateOf(post.isSaved) }
    var currentScore by remember { mutableStateOf(post.score) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPostClick(post) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Vote buttons
            VoteButtons(
                score = currentScore,
                isUpvoted = isUpvoted,
                isDownvoted = isDownvoted,
                onUpvote = {
                    when {
                        isUpvoted -> {
                            isUpvoted = false
                            currentScore -= 1
                        }
                        isDownvoted -> {
                            isDownvoted = false
                            isUpvoted = true
                            currentScore += 2
                        }
                        else -> {
                            isUpvoted = true
                            currentScore += 1
                        }
                    }
                },
                onDownvote = {
                    when {
                        isDownvoted -> {
                            isDownvoted = false
                            currentScore += 1
                        }
                        isUpvoted -> {
                            isUpvoted = false
                            isDownvoted = true
                            currentScore -= 2
                        }
                        else -> {
                            isDownvoted = true
                            currentScore -= 1
                        }
                    }
                },
                modifier = Modifier.padding(end = 8.dp)
            )

            // Post content
            Column(modifier = Modifier.weight(1f)) {
                // Subreddit and metadata
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = post.subreddit.name,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable { onSubredditClick(post.subreddit.id) }
                    )
                    Text(
                        text = " • ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Posted by ${post.author.username} ${post.timePosted}",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Post title
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        if (post.flair != null) {
                            Text(
                                text = post.flair,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                        RoundedCornerShape(2.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                        
                        Text(
                            text = post.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Thumbnail
                    if (post.thumbnailUrl != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        AsyncImage(
                            model = post.thumbnailUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                // Image post
                if (post.type == PostType.IMAGE && post.imageUrl != null && post.thumbnailUrl == null) {
                    AsyncImage(
                        model = post.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .padding(bottom = 8.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Text content preview
                if (post.content.isNotEmpty() && post.type == PostType.TEXT) {
                    Text(
                        text = post.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Action buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ActionButton(
                        icon = Icons.Outlined.ChatBubbleOutline,
                        text = "${post.commentCount}",
                        onClick = { onPostClick(post) }
                    )
                    
                    ActionButton(
                        icon = Icons.Outlined.Share,
                        text = "Share",
                        onClick = { }
                    )
                    
                    ActionButton(
                        icon = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        text = if (isSaved) "Saved" else "Save",
                        onClick = { isSaved = !isSaved }
                    )

                    if (post.awardCount > 0) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "Awards",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "${post.awardCount}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
    
    Divider(
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
        thickness = 1.dp
    )
}

@Composable
private fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        modifier = Modifier.height(32.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(18.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
