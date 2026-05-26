package com.reddit.clone.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.reddit.clone.data.SampleData
import com.reddit.clone.ui.screens.*

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeFeedScreen(
                onPostClick = { post ->
                    navController.navigate("${Screen.PostDetail.route}/${post.id}")
                },
                onSubredditClick = { subredditId ->
                    navController.navigate("${Screen.Subreddit.route}/$subredditId")
                },
                onSearchClick = {
                    navController.navigate(Screen.Search.route)
                },
                onCreatePostClick = {
                    navController.navigate(Screen.CreatePost.route)
                }
            )
        }
        
        composable(Screen.Popular.route) {
            PopularScreen(
                onPostClick = { post ->
                    navController.navigate("${Screen.PostDetail.route}/${post.id}")
                },
                onSubredditClick = { subredditId ->
                    navController.navigate("${Screen.Subreddit.route}/$subredditId")
                }
            )
        }
        
        composable(Screen.Communities.route) {
            CommunitiesScreen(
                onSubredditClick = { subredditId ->
                    navController.navigate("${Screen.Subreddit.route}/$subredditId")
                }
            )
        }
        
        composable(Screen.Chat.route) {
            ChatScreen()
        }
        
        composable(Screen.Profile.route) {
            ProfileScreen(
                onPostClick = { post ->
                    navController.navigate("${Screen.PostDetail.route}/${post.id}")
                }
            )
        }
        
        composable("${Screen.PostDetail.route}/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            val post = SampleData.samplePosts.find { it.id == postId }
            
            if (post != null) {
                PostDetailScreen(
                    post = post,
                    onBackClick = { navController.popBackStack() },
                    onSubredditClick = { subredditId ->
                        navController.navigate("${Screen.Subreddit.route}/$subredditId")
                    }
                )
            }
        }
        
        composable(Screen.Search.route) {
            SearchScreen(
                onBackClick = { navController.popBackStack() },
                onPostClick = { post ->
                    navController.navigate("${Screen.PostDetail.route}/${post.id}")
                },
                onSubredditClick = { subredditId ->
                    navController.navigate("${Screen.Subreddit.route}/$subredditId")
                }
            )
        }
        
        composable(Screen.CreatePost.route) {
            CreatePostScreen(
                onBackClick = { navController.popBackStack() },
                onPostCreated = {
                    navController.popBackStack()
                }
            )
        }
        
        composable("${Screen.Subreddit.route}/{subredditId}") { backStackEntry ->
            val subredditId = backStackEntry.arguments?.getString("subredditId")
            val subreddit = SampleData.sampleSubreddits.find { it.id == subredditId }
            
            if (subreddit != null) {
                SubredditScreen(
                    subreddit = subreddit,
                    onBackClick = { navController.popBackStack() },
                    onPostClick = { post ->
                        navController.navigate("${Screen.PostDetail.route}/${post.id}")
                    }
                )
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Popular : Screen("popular")
    object Communities : Screen("communities")
    object Chat : Screen("chat")
    object Profile : Screen("profile")
    object PostDetail : Screen("post_detail")
    object Search : Screen("search")
    object CreatePost : Screen("create_post")
    object Subreddit : Screen("subreddit")
}
