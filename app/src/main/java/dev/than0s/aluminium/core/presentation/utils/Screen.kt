package dev.than0s.aluminium.core.presentation.utils

import androidx.navigation.NavDestination
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object SplashScreen : Screen()

    @Serializable
    data object SignInScreen : Screen()

    @Serializable
    data object SettingScreen : Screen()

    @Serializable
    data object RegistrationScreen : Screen()

    @Serializable
    data object RegistrationRequestsScreen : Screen()

    @Serializable
    data object PostUploadScreen : Screen()

    @Serializable
    data object SignOutScreen : Screen()

    @Serializable
    data object ForgotPasswordScreen : Screen()

    @Serializable
    data object ChatListScreen : Screen()

    @Serializable
    data object CreateProfileScreen : Screen()

    @Serializable
    data class PostsScreen(
        val userId: String? = null,
    ) : Screen()

    @Serializable
    data class ProfileScreen(
        val userId: String,
    ) : Screen()

    @Serializable
    data class CommentsScreen(
        val postId: String,
    ) : Screen()

    @Serializable
    data class ChatDetailScreen(
        val userId: String
    ) : Screen()

    class ProfileTabScreen {
        @Serializable
        data class AboutScreen(
            val userId: String
        ) : Screen()

        @Serializable
        data class ContactScreen(
            val userId: String
        ) : Screen()

        @Serializable
        data class PostsScreen(
            val userId: String
        ) : Screen()
    }
}

fun getScreenName(screenClassName: String?): String? {
    return when (screenClassName) {
        Screen.SplashScreen::class.simpleName -> "Splash"
        Screen.SignInScreen::class.simpleName -> "Sign In"
        Screen.SettingScreen::class.simpleName -> "Settings"
        Screen.RegistrationScreen::class.simpleName -> "Registration"
        Screen.RegistrationRequestsScreen::class.simpleName -> "Registration Request"
        Screen.PostUploadScreen::class.simpleName -> "Post Upload"
        Screen.SignOutScreen::class.simpleName -> "Sign Out"
        Screen.ForgotPasswordScreen::class.simpleName -> "Forgot Password"
        Screen.PostsScreen::class.simpleName -> "Posts"
        Screen.ProfileScreen::class.simpleName -> "Profile"
        Screen.CommentsScreen::class.simpleName -> "Comments"
        else -> null
    }
}

fun getClassNameFromNavGraph(nav: NavDestination?): String? {
    return nav?.route
        ?.substringAfterLast(".")
        ?.substringBefore("/")
        ?.substringBefore("?")
}