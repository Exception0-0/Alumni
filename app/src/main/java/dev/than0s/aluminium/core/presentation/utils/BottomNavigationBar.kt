package dev.than0s.aluminium.core.presentation.utils

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.currentUserRole

private var customNavbar by mutableStateOf<(@Composable (RowScope.() -> Unit))?>(null)

private data class BottomNavigationItem(
    val screen: Screen,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
)

private val bottomNavItems = listOf(
    BottomNavigationItem(
        Screen.RegistrationRequestsScreen,
        Icons.Filled.Home,
        Icons.Outlined.Home,
    ),
    BottomNavigationItem(
        Screen.HomeScreen(),
        Icons.Filled.Home,
        Icons.Outlined.Home,
    ),
    BottomNavigationItem(
        Screen.ChatsScreen,
        Icons.AutoMirrored.Filled.Chat,
        Icons.AutoMirrored.Outlined.Chat,
    ),
    BottomNavigationItem(
        Screen.NotificationScreen,
        Icons.Filled.Notifications,
        Icons.Outlined.Notifications,
    ),
    BottomNavigationItem(
        Screen.SettingScreen,
        Icons.Filled.Settings,
        Icons.Outlined.Settings,
    ),
)

private fun isGiveScreenHaveBottomBar(screenClassName: String?): Boolean {
    return bottomNavItems.any {
        it.screen::class.simpleName == screenClassName
    }
}

private fun shouldShowOption(screen: Screen): Boolean {
    return when (screen) {
        is Screen.HomeScreen -> {
            currentUserRole != Role.Admin
        }

        is Screen.ChatsScreen -> {
            currentUserRole != Role.Admin
        }

        is Screen.RegistrationRequestsScreen -> {
            currentUserRole == Role.Admin
        }

        is Screen.NotificationScreen -> {
            currentUserRole != Role.Admin
        }

        is Screen.SettingScreen -> true
        else -> false
    }
}

@Composable
fun AluminiumBottomNavigationBar(
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreenClassName = getClassNameFromNavGraph(navBackStackEntry?.destination)

    if (isGiveScreenHaveBottomBar(currentScreenClassName)) {
        NavigationBar(
            content = customNavbar ?: {
                bottomNavItems.forEach { item ->
                    if (shouldShowOption(item.screen)) {
                        val isScreenSelected =
                            currentScreenClassName == item.screen::class.simpleName
                        NavigationBarItem(
                            selected = isScreenSelected,
                            onClick = {
                                navController.replace(item.screen)
                            },
                            icon = {
                                Icon(
                                    imageVector = if (isScreenSelected) {
                                        item.filledIcon
                                    } else {
                                        item.outlinedIcon
                                    },
                                    contentDescription = item.screen::class.simpleName,
                                )
                            },
                            label = {
                                Text(text = item.screen.name)
                            },
                            alwaysShowLabel = false,
                        )
                    }
                }
            }
        )
    }
}