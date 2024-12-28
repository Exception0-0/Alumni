package dev.than0s.aluminium.core.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.than0s.aluminium.core.presentation.composable.AluminiumTitleText
import dev.than0s.aluminium.ui.textSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AluminiumTopAppBar(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = currentBackStackEntry?.destination
    getDefaultTopAppBar(destination)?.let {
        LargeTopAppBar(
            title = {
                AluminiumTitleText(
                    title = it.title,
                    fontSize = if (scrollBehavior.state.collapsedFraction == 1f) {
                        MaterialTheme.textSize.huge
                    } else {
                        MaterialTheme.textSize.gigantic
                    }
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
            colors = TopAppBarDefaults.topAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            scrollBehavior = scrollBehavior
        )
    }
}

private fun getDefaultTopAppBar(destination: NavDestination?): TopAppBarItem? {
    return when {
        destination == null -> null
        destination.hasRoute<Screen.SignInScreen>() -> TopAppBarItem(
            title = Screen.SignInScreen.name,
            shouldHaveNavIcon = false
        )

        destination.hasRoute<Screen.ForgotPasswordScreen>() -> TopAppBarItem(
            title = Screen.ForgotPasswordScreen.name
        )

        destination.hasRoute<Screen.RegistrationScreen>() -> TopAppBarItem(
            title = Screen.RegistrationScreen.name
        )

        destination.hasRoute<Screen.PostsScreen>() -> TopAppBarItem(
            title = Screen.PostsScreen().name,
            shouldHaveNavIcon = false
        )

        destination.hasRoute<Screen.ProfileScreen>() -> TopAppBarItem(
            title = Screen.ProfileScreen("").name,
        )

        destination.hasRoute<Screen.SettingScreen>() -> TopAppBarItem(
            title = Screen.SettingScreen.name,
            shouldHaveNavIcon = false
        )

        else -> null
    }
}

private data class TopAppBarItem(
    val title: String,
    val shouldHaveNavIcon: Boolean = true,
)