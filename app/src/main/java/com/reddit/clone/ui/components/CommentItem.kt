package com.reddit.clone.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reddit.clone.data.models.Comment

@Composable
fun CommentItem(
    comment: Comment,
    onReplyClick: (Comment) -> Unit,
    modifier: Modifier = Modifier
) {
    var isUpvoted by remember { mutableStateOf(comment.isUpvoted) }
    var isDownvoted by remember { mutableStateOf(comment.isDownvoted) }
    var currentScore by remember { mutableStateOf(comment.score) }
    var isCollapsed by remember { mutableStateOf(comment.isCollapsed) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = (comment.depth * 16).dp,
                top = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            )
    ) {
        // Collapse indicator for nested comments
        if (comment.depth > 0) {
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(if (isCollapsed) 12.dp else 0.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Vertical line for nested comments
            if (comment.depth > 0 && !isCollapsed) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                        .padding(end = 12.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                // Comment header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isCollapsed = !isCollapsed }
                ) {
                    Text(
                        text = comment.author.username,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    if (comment.isOP) {
                        Text(
                            text = "OP",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                    RoundedCornerShape(2.dp)
                                )
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }
                    
                    Text(
                        text = " • ${comment.timePosted}",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (!isCollapsed) {
                    // Comment content
                    Text(
                        text = comment.content,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Comment actions
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
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

                        TextButton(
                            onClick = { onReplyClick(comment) },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            modifier = Modifier.height(28.dp)
                        ) {
                            Text(
                                text = "Reply",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        if (comment.awardCount > 0) {
                            Text(
                                text = "🏆 ${comment.awardCount}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Render replies
                    comment.replies.forEach { reply ->
                        CommentItem(
                            comment = reply,
                            onReplyClick = onReplyClick
                        )
                    }
                }
            }
        }
    }
}
