package com.reddit.clone.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.reddit.clone.data.SampleData
import com.reddit.clone.data.models.Post
import com.reddit.clone.ui.components.PostCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularScreen(
    onPostClick: (Post) -> Unit,
    onSubredditClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Show posts sorted by upvotes (popular)
    val popularPosts = remember {
        SampleData.samplePosts.sortedByDescending { it.upvotes }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Popular",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(popularPosts) { post ->
                PostCard(
                    post = post,
                    onPostClick = onPostClick,
                    onSubredditClick = onSubredditClick
                )
            }
        }
    }
}
