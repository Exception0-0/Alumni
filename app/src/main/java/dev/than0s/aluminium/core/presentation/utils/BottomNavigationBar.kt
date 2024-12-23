package dev.than0s.aluminium.core.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AppRegistration
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


private data class BottomNavigationItem(
    val screen: Screen,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
)

private val bottomNavItems = listOf(
    BottomNavigationItem(
        Screen.RegistrationRequestsScreen,
        Icons.Filled.AppRegistration,
        Icons.Outlined.AppRegistration,
    ),
    BottomNavigationItem(
        Screen.PostsScreen(),
        Icons.Filled.Face,
        Icons.Outlined.Face,
    ),
    BottomNavigationItem(
        Screen.ChatListScreen,
        Icons.AutoMirrored.Filled.Chat,
        Icons.AutoMirrored.Outlined.Chat,
    ),
    BottomNavigationItem(
        Screen.SettingScreen,
        Icons.Filled.Settings,
        Icons.Outlined.Settings,
    )
)

private fun getScreenName(screen: Screen): String? {
    return screen::class.simpleName
}

private fun getCurrentScreenName(navBackStackEntry: NavBackStackEntry?): String? {
    return navBackStackEntry
        ?.destination
        ?.route
        ?.substringAfterLast(".")
        ?.substringBefore("/")
        ?.substringBefore("?")
}

private fun isCurrentScreenHaveBottomBar(currentScreenName: String?): Boolean {
    return bottomNavItems.any {
        getScreenName(it.screen) == currentScreenName
    }
}

private fun shouldShowOption(screen: Screen): Boolean {
    return true
}

@Composable
fun AluminiumBottomNavigationBar(
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreenName = getCurrentScreenName(navBackStackEntry)

    if (isCurrentScreenHaveBottomBar(currentScreenName)) {
        NavigationBar {
            bottomNavItems.forEach { item ->
                if (shouldShowOption(item.screen)) {
                    val isScreenSelected = currentScreenName == getScreenName(item.screen)
                    NavigationBarItem(
                        selected = isScreenSelected,
                        onClick = {
                            navController.popAndOpen(item.screen)
                        },
                        icon = {
                            Icon(
                                imageVector = if (isScreenSelected) {
                                    item.filledIcon
                                } else {
                                    item.outlinedIcon
                                },
                                contentDescription = item.screen::class.simpleName
                            )
                        },
                        label = {
//                            Text(
//                                text = item.screen::class.simpleName
//                            )
                        }
                    )
                }
            }
        }
    }
}