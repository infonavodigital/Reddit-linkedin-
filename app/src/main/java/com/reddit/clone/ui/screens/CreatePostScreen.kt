package com.reddit.clone.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.reddit.clone.data.SampleData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    onBackClick: () -> Unit,
    onPostCreated: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedPostType by remember { mutableStateOf(PostTypeTab.TEXT) }
    var selectedSubreddit by remember { mutableStateOf(SampleData.sampleSubreddits.first()) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var linkUrl by remember { mutableStateOf("") }
    var showSubredditPicker by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create a post",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            // Handle post creation
                            onPostCreated()
                        },
                        enabled = title.isNotBlank() && (
                            (selectedPostType == PostTypeTab.TEXT && content.isNotBlank()) ||
                            (selectedPostType == PostTypeTab.LINK && linkUrl.isNotBlank()) ||
                            selectedPostType == PostTypeTab.IMAGE
                        )
                    ) {
                        Text(
                            text = "Post",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Subreddit selector
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showSubredditPicker = true }
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(32.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = selectedSubreddit.name.substring(2, 3).uppercase(),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = selectedSubreddit.name,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "${selectedSubreddit.memberCount} members",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Select subreddit"
                    )
                }
            }
            
            Divider()
            
            // Post type tabs
            TabRow(
                selectedTabIndex = selectedPostType.ordinal,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                PostTypeTab.values().forEach { type ->
                    Tab(
                        selected = selectedPostType == type,
                        onClick = { selectedPostType = type },
                        text = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                Icon(
                                    imageVector = type.icon,
                                    contentDescription = type.label
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = type.label,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    )
                }
            }
            
            Divider()
            
            // Post content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Title
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("An interesting title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Content based on post type
                when (selectedPostType) {
                    PostTypeTab.TEXT -> {
                        OutlinedTextField(
                            value = content,
                            onValueChange = { content = it },
                            placeholder = { Text("Text (optional)") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            )
                        )
                    }
                    PostTypeTab.IMAGE -> {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AddPhotoAlternate,
                                        contentDescription = "Add image",
                                        modifier = Modifier.size(48.dp),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Tap to add image",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                    PostTypeTab.LINK -> {
                        OutlinedTextField(
                            value = linkUrl,
                            onValueChange = { linkUrl = it },
                            placeholder = { Text("URL") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Link,
                                    contentDescription = "Link"
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            )
                        )
                    }
                    PostTypeTab.POLL -> {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedTextField(
                                value = "",
                                onValueChange = { },
                                placeholder = { Text("Option 1") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            OutlinedTextField(
                                value = "",
                                onValueChange = { },
                                placeholder = { Text("Option 2") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            TextButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add option"
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Add option")
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class PostTypeTab(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    TEXT("Text", Icons.Default.Subject),
    IMAGE("Image", Icons.Default.Image),
    LINK("Link", Icons.Default.Link),
    POLL("Poll", Icons.Default.Poll)
}
