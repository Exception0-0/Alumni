package dev.than0s.aluminium.core.presentation.utils

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.than0s.aluminium.ui.textSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AluminiumTopAppBar(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = currentBackStackEntry?.destination
    getDefaultTopAppBar(
        destination = destination,
        openScreen = navController::openScreen
    )?.let {
        val collapsedFraction by derivedStateOf {
            scrollBehavior.state.collapsedFraction
        }
        val titleSize = lerp(
            start = MaterialTheme.textSize.large.value,
            stop = MaterialTheme.textSize.gigantic.value,
            fraction = 1f - collapsedFraction
        ).sp
        LargeTopAppBar(
            title = {
                Text(
                    text = it.title,
                    fontSize = titleSize,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                if (it.shouldHaveNavIcon) {
                    IconButton(onClick = {
                        navController.popScreen()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back button"
                        )
                    }
                }
            },
            actions = it.actions,
            colors = TopAppBarDefaults.topAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            scrollBehavior = scrollBehavior
        )
    }
}

private fun getDefaultTopAppBar(
    destination: NavDestination?,
    openScreen: (Screen) -> Unit
): TopAppBarItem? {
    return when {
        destination == null -> null
        destination.hasRoute<Screen.SignInScreen>() -> TopAppBarItem(
            title = Screen.SignInScreen.name,
            shouldHaveNavIcon = false,
            actions = {
                IconButton(
                    onClick = {
                        openScreen(Screen.AppearanceScreen)
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = "Appearance"
                        )
                    }
                )
            }
        )

        destination.hasRoute<Screen.ForgotPasswordScreen>() -> TopAppBarItem(
            title = Screen.ForgotPasswordScreen.name
        )

        destination.hasRoute<Screen.RegistrationScreen>() -> TopAppBarItem(
            title = Screen.RegistrationScreen.name
        )

        destination.hasRoute<Screen.HomeScreen>() -> TopAppBarItem(
            title = Screen.HomeScreen().name,
            shouldHaveNavIcon = false
        )

        destination.hasRoute<Screen.ProfileScreen>() or
                destination.hasRoute<Screen.UpdateProfileDialog>() or
                destination.hasRoute<Screen.UpdateContactDialog>() -> {
            TopAppBarItem(
                title = Screen.ProfileScreen("").name,
            )
        }

        destination.hasRoute<Screen.SettingScreen>() -> TopAppBarItem(
            title = Screen.SettingScreen.name,
            shouldHaveNavIcon = false
        )

        destination.hasRoute<Screen.AppearanceScreen>() -> TopAppBarItem(
            title = Screen.AppearanceScreen.name,
        )

        destination.hasRoute<Screen.ChatsScreen>() -> TopAppBarItem(
            title = Screen.ChatsScreen.name,
            shouldHaveNavIcon = false
        )

        destination.hasRoute<Screen.CommentsScreen>() -> TopAppBarItem(
            title = Screen.CommentsScreen("").name
        )

        else -> null
    }
}

private data class TopAppBarItem(
    val title: String,
    val shouldHaveNavIcon: Boolean = true,
    val actions: @Composable (RowScope.() -> Unit) = {},
)