package dev.than0s.aluminium.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import dev.than0s.aluminium.features.auth.presentation.screens.forget_password.ForgetPasswordScreen
import dev.than0s.aluminium.features.auth.presentation.screens.sign_in.SignInScreen
import dev.than0s.aluminium.features.chat.presentation.screens.detail_chat.ScreenDetailChat
import dev.than0s.aluminium.features.chat.presentation.screens.group_list.ScreenGroupList
import dev.than0s.aluminium.features.post.presentation.screens.comments.CommentScreen
import dev.than0s.aluminium.features.post.presentation.screens.post_upload.PostUploadScreen
import dev.than0s.aluminium.features.post.presentation.screens.posts.PostsScreen
import dev.than0s.aluminium.features.profile.presentation.dialogs.update_contacts.UpdateContactScreen
import dev.than0s.aluminium.features.profile.presentation.dialogs.update_profile.UpdateProfileDialog
import dev.than0s.aluminium.features.profile.presentation.screens.profile.ProfileScreen
import dev.than0s.aluminium.features.profile.presentation.screens.settings.SettingScreen
import dev.than0s.aluminium.features.registration.presentation.screens.registration.RegistrationScreen
import dev.than0s.aluminium.features.registration.presentation.screens.requests.RegistrationRequestsScreen
import dev.than0s.aluminium.features.settings.screens.appearance.AppearanceScreen
import dev.than0s.aluminium.features.splash.presentation.splash.SplashScreen

@Composable
fun NavGraphHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen,
        modifier = modifier
    ) {
        composable<Screen.SplashScreen> {
            SplashScreen(
                replaceScreen = navController::replace
            )
        }
        composable<Screen.SignInScreen> {
            SignInScreen(
                openScreen = navController::openScreen,
                restartApp = navController::restartApp
            )
        }
        composable<Screen.RegistrationScreen> {
            RegistrationScreen(
                popScreen = navController::popScreen
            )
        }
        composable<Screen.SettingScreen> {
            SettingScreen(
                openScreen = navController::openScreen,
                restartApp = navController::restartApp
            )
        }
        composable<Screen.RegistrationRequestsScreen> {
            RegistrationRequestsScreen()
        }
        composable<Screen.PostUploadScreen> {
            PostUploadScreen(
                popScreen = navController::popScreen
            )
        }
        composable<Screen.ForgotPasswordScreen> {
            ForgetPasswordScreen(
                popScreen = navController::popScreen
            )
        }
        composable<Screen.HomeScreen> {
            PostsScreen(
                openScreen = navController::openScreen
            )
        }
        composable<Screen.ProfileScreen> {
            ProfileScreen(
                openScreen = navController::openScreen
            )
        }
        composable<Screen.CommentsScreen> {
            CommentScreen(
                openScreen = navController::openScreen
            )
        }
        dialog<Screen.UpdateProfileDialog> {
            UpdateProfileDialog(
                onSuccess = navController::popScreen
            )
        }
        dialog<Screen.UpdateContactDialog> {
            UpdateContactScreen(
                popScreen = navController::popScreen
            )
        }
        dialog<Screen.CreateProfileDialog> {
            UpdateProfileDialog(
                onSuccess = navController::restartApp,
                shouldSignOutShow = true,
            )
        }
        composable<Screen.AppearanceScreen> {
            AppearanceScreen()
        }
        composable<Screen.ChatsScreen> {
            ScreenGroupList(
                openScreen = navController::openScreen
            )
        }
        composable<Screen.ChatDetailScreen> {
            ScreenDetailChat(
                popScreen = navController::popScreen
            )
        }
        composable<Screen.NotificationScreen> {

        }
    }
}

fun NavHostController.openScreen(screen: Screen) {
    navigate(screen)
}

fun NavHostController.popScreen() {
    navigateUp()
}

fun NavHostController.popAndOpen(screen: Screen) {
    navigateUp()
    navigate(screen)
}

fun NavHostController.replace(screen: Screen) {
    navigate(screen) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavHostController.restartApp() {
    navigate(Screen.SplashScreen) {
        popUpTo(graph.findStartDestination().id) {
            inclusive = true
        }
    }
}