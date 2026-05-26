package com.reddit.clone.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reddit.clone.ui.theme.DownvoteBlue
import com.reddit.clone.ui.theme.UpvoteOrange

@Composable
fun VoteButtons(
    score: Int,
    isUpvoted: Boolean,
    isDownvoted: Boolean,
    onUpvote: () -> Unit,
    onDownvote: () -> Unit,
    modifier: Modifier = Modifier,
    vertical: Boolean = true
) {
    if (vertical) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // <-- Direct pass kiya type strictness ke liye
        ) {
            VoteContent(
                score = score,
                isUpvoted = isUpvoted,
                isDownvoted = isDownvoted,
                onUpvote = onUpvote,
                onDownvote = onDownvote,
                vertical = true
            )
        }
    } else {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start // <-- Direct pass kiya type strictness ke liye
        ) {
            VoteContent(
                score = score,
                isUpvoted = isUpvoted,
                isDownvoted = isDownvoted,
                onUpvote = onUpvote,
                onDownvote = onDownvote,
                vertical = false
            )
        }
    }
}

@Composable
private fun VoteContent(
    score: Int,
    isUpvoted: Boolean,
    isDownvoted: Boolean,
    onUpvote: () -> Unit,
    onDownvote: () -> Unit,
    vertical: Boolean
) {
    // Upvote button
    IconButton(
        onClick = onUpvote,
        modifier = Modifier.size(if (vertical) 32.dp else 28.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowUpward,
            contentDescription = "Upvote",
            tint = if (isUpvoted) UpvoteOrange else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
    }
    
    // Score
    Text(
        text = formatScore(score),
        style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = FontWeight.Bold,
            fontSize = if (vertical) 12.sp else 13.sp
        ),
        color = when {
            isUpvoted -> UpvoteOrange
            isDownvoted -> DownvoteBlue
            else -> MaterialTheme.colorScheme.onSurface
        },
        modifier = Modifier.padding(horizontal = if (vertical) 0.dp else 4.dp)
    )
    
    // Downvote button
    IconButton(
        onClick = onDownvote,
        modifier = Modifier.size(if (vertical) 32.dp else 28.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowDownward,
            contentDescription = "Downvote",
            tint = if (isDownvoted) DownvoteBlue else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
    }
}

fun formatScore(score: Int): String {
    return when {
        score >= 10000 -> "${score / 1000}k"
        score >= 1000 -> String.format("%.1fk", score / 1000.0)
        else -> score.toString()
    }
}
