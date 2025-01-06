package dev.than0s.aluminium.core.presentation.utils

import androidx.navigation.NavDestination
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(
    val name: String,
) {
    @Serializable
    data object SplashScreen : Screen(name = "Splash")

    @Serializable
    data object SignInScreen : Screen(name = "Sign In")

    @Serializable
    data object SettingScreen : Screen(name = "Settings")

    @Serializable
    data object RegistrationScreen : Screen(name = "Registration")

    @Serializable
    data object RegistrationRequestsScreen : Screen(name = "Admin Home")

    @Serializable
    data object PostUploadScreen : Screen(name = "Post Upload")

    @Serializable
    data object ForgotPasswordScreen : Screen(name = "Forgot Password")

    @Serializable
    data object ChatsScreen : Screen(name = "Chats")

    @Serializable
    data object AppearanceScreen : Screen(name = "Appearance")

    @Serializable
    data object UpdateProfileDialog : Screen(name = "Update Profile")

    @Serializable
    data object UpdateContactDialog : Screen(name = "Update Contact")

    @Serializable
    data class HomeScreen(
        val userId: String? = null,
    ) : Screen("Home")

    @Serializable
    data class ProfileScreen(
        val userId: String,
    ) : Screen("Profile")

    @Serializable
    data class CommentsScreen(
        val postId: String,
    ) : Screen("Comments")

    @Serializable
    data class ChatDetailScreen(
        val userId: String
    ) : Screen("Chat Detail")

    class ProfileTabScreen {
        @Serializable
        data class AboutScreen(
            val userId: String
        ) : Screen("About")

        @Serializable
        data class ContactScreen(
            val userId: String
        ) : Screen("Contact")

        @Serializable
        data class PostsScreen(
            val userId: String
        ) : Screen("Posts")
    }
}

fun getClassNameFromNavGraph(nav: NavDestination?): String? {
    return nav?.route
        ?.substringAfterLast(".")
        ?.substringBefore("/")
        ?.substringBefore("?")
}