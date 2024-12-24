package dev.than0s.aluminium.core.presentation.utils

import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

private var customNavbar by mutableStateOf<(@Composable (RowScope.() -> Unit))?>(null)

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

private fun isGiveScreenHaveBottomBar(screenClassName: String?): Boolean {
    return bottomNavItems.any {
        it.screen::class.simpleName == screenClassName
    }
}

private fun shouldShowOption(screen: Screen): Boolean {
    return true
}

private fun getDefaultBottomBar() {

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
                                Text(
                                    text = getScreenName(item.screen::class.simpleName) ?: "Than0s"
                                )
                            }
                        )
                    }
                }
            }
        )
    }
}