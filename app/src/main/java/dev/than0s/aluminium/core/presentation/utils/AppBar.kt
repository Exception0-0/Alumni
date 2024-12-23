package dev.than0s.aluminium.core.presentation.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AluminiumTopAppBar(
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val topAppBarList = remember {
        listOf(
            TopAppBarItem(
                uid = "dev.than0s.aluminium.core.Screen.PostsScreen?userId={userId}",
                label = "Posts"
            ),
            TopAppBarItem(
                uid = "dev.than0s.aluminium.core.Screen.CommentsScreen/{postId}",
                label = "Comments"
            ),
            TopAppBarItem(
                uid = "dev.than0s.aluminium.core.Screen.ChatListScreen",
                label = "Chat"
            ),
            TopAppBarItem(
                uid = "dev.than0s.aluminium.core.Screen.SettingScreen",
                label = "Settings"
            )
        )
    }

    val isCurrentScreenHaveTopBar = topAppBarList.any { it.uid == currentRoute }

    if (isCurrentScreenHaveTopBar) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(text = topAppBarList.find { it.uid == currentRoute }?.label ?: "Aluminium")
            },
        )
    }
}

private data class TopAppBarItem(
    val uid: String,
    val label: String,
)