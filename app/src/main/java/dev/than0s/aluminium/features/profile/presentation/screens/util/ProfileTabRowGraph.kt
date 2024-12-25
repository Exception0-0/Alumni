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
import kotlinx.serialization.Serializable

@Composable
fun ProfileNavHost(
    userId: String,
    navController: NavHostController,
    openScreen: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ProfileTabScreen.AboutScreen(
            userId = userId
        ),
        modifier = modifier
    ) {
        composable<ProfileTabScreen.AboutScreen> {
            AboutScreen()
        }
        composable<ProfileTabScreen.ContactScreen> {
            ContactScreen()
        }
        composable<ProfileTabScreen.PostsScreen> {
            PostsScreen(
                openScreen = openScreen
            )
        }
    }
}

sealed class ProfileTabScreen {
    @Serializable
    data class AboutScreen(
        val userId: String
    ) : ProfileTabScreen()

    @Serializable
    data class ContactScreen(
        val userId: String
    ) : ProfileTabScreen()

    @Serializable
    data class PostsScreen(
        val userId: String
    ) : ProfileTabScreen()
}

fun NavHostController.replace(screen: ProfileTabScreen) {
    popBackStack()
    navigate(screen)
}

