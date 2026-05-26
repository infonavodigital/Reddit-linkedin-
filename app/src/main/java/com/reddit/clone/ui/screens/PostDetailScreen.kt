package com.reddit.clone.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.reddit.clone.data.SampleData
import com.reddit.clone.data.models.Comment
import com.reddit.clone.data.models.Post
import com.reddit.clone.data.models.PostType
import com.reddit.clone.ui.components.CommentItem
import com.reddit.clone.ui.components.VoteButtons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    post: Post,
    onBackClick: () -> Unit,
    onSubredditClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val comments = remember { SampleData.getSampleComments(post.id) }
    var showCommentInput by remember { mutableStateOf(false) }
    var commentText by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = post.subreddit.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            if (showCommentInput) {
                CommentInputBar(
                    commentText = commentText,
                    onCommentTextChange = { commentText = it },
                    onSendClick = {
                        // Handle comment submission
                        commentText = ""
                        showCommentInput = false
                    },
                    onCancelClick = {
                        commentText = ""
                        showCommentInput = false
                    }
                )
            }
        },
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Post content
            item {
                PostDetailContent(
                    post = post,
                    onSubredditClick = onSubredditClick,
                    onCommentClick = { showCommentInput = true }
                )
                
                Divider(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    thickness = 8.dp
                )
            }
            
            // Comments header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${post.commentCount} Comments",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    TextButton(onClick = { }) {
                        Text(
                            text = "Sort by: Best",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
            
            // Comments list
            items(comments) { comment ->
                CommentItem(
                    comment = comment,
                    onReplyClick = { showCommentInput = true }
                )
                Divider(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
private fun PostDetailContent(
    post: Post,
    onSubredditClick: (String) -> Unit,
    onCommentClick: () -> Unit
) {
    var isUpvoted by remember { mutableStateOf(post.isUpvoted) }
    var isDownvoted by remember { mutableStateOf(post.isDownvoted) }
    var isSaved by remember { mutableStateOf(post.isSaved) }
    var currentScore by remember { mutableStateOf(post.score) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        // Subreddit and author info
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 12.dp)
                .clickable { onSubredditClick(post.subreddit.id) }
        ) {
            Surface(
                modifier = Modifier.size(32.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = post.subreddit.name.substring(2, 3).uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Column {
                Text(
                    text = post.subreddit.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${post.author.username} • ${post.timePosted}",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Post title
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
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        Text(
            text = post.title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Post content
        if (post.content.isNotEmpty()) {
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        // Image post
        if (post.type == PostType.IMAGE && post.imageUrl != null) {
            AsyncImage(
                model = post.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .padding(bottom = 12.dp),
                contentScale = ContentScale.Crop
            )
        }

        // Link post
        if (post.type == PostType.LINK && post.linkUrl != null) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = post.linkUrl,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Vote and action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
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
                vertical = false
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onCommentClick) {
                    Text(
                        text = "💬 Comment",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "Share"
                    )
                }
                
                IconButton(onClick = { isSaved = !isSaved }) {
                    Icon(
                        imageVector = Icons.Outlined.BookmarkBorder,
                        contentDescription = "Save"
                    )
                }
            }
        }
    }
}

@Composable
private fun CommentInputBar(
    commentText: String,
    onCommentTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = commentText,
                    onValueChange = onCommentTextChange,
                    placeholder = { Text("Add a comment...") },
                    modifier = Modifier.weight(1f),
                    maxLines = 3
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Button(
                        onClick = onSendClick,
                        enabled = commentText.isNotBlank()
                    ) {
                        Text("Post")
                    }
                    
                    TextButton(onClick = onCancelClick) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}
