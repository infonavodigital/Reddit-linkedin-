package com.reddit.clone.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reddit.clone.data.SampleData
import com.reddit.clone.data.models.Post
import com.reddit.clone.ui.components.PostCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFeedScreen(
    onPostClick: (Post) -> Unit,
    onSubredditClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onCreatePostClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf(FeedFilter.BEST) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Reddit Clone",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreatePostClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Post",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Feed filter tabs
            FeedFilterTabs(
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it }
            )
            
            Divider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                thickness = 1.dp
            )
            
            // Posts list
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(SampleData.samplePosts) { post ->
                    PostCard(
                        post = post,
                        onPostClick = onPostClick,
                        onSubredditClick = onSubredditClick
                    )
                }
            }
        }
    }
}

@Composable
private fun FeedFilterTabs(
    selectedFilter: FeedFilter,
    onFilterSelected: (FeedFilter) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedFilter.ordinal,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        edgePadding = 0.dp,
        indicator = {}
    ) {
        FeedFilter.values().forEach { filter ->
            Tab(
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                text = {
                    Text(
                        text = filter.displayName,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = if (selectedFilter == filter) 
                                FontWeight.Bold 
                            else 
                                FontWeight.Normal,
                            fontSize = 14.sp
                        ),
                        color = if (selectedFilter == filter)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }
    }
}

enum class FeedFilter(val displayName: String) {
    BEST("Best"),
    HOT("Hot"),
    NEW("New"),
    TOP("Top"),
    RISING("Rising")
}
