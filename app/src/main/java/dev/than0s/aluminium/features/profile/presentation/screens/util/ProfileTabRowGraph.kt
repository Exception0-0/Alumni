package dev.than0s.aluminium.features.profile.presentation.screens.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.than0s.aluminium.core.presentation.utils.Screen
import dev.than0s.aluminium.features.profile.presentation.screens.about.AboutScreen
import dev.than0s.aluminium.features.profile.presentation.screens.contact.ContactScreen
import dev.than0s.aluminium.features.profile.presentation.screens.post.PostsScreen

@Composable
fun ProfileNavHost(
    userId: String,
    navController: NavHostController,
    openScreen: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ProfileTabScreen.AboutScreen(
            userId = userId
        ),
        modifier = modifier
    ) {
        composable<Screen.ProfileTabScreen.AboutScreen> {
            AboutScreen()
        }
        composable<Screen.ProfileTabScreen.ContactScreen> {
            ContactScreen()
        }
        composable<Screen.ProfileTabScreen.PostsScreen> {
            PostsScreen(
                openScreen = openScreen
            )
        }
    }
}


