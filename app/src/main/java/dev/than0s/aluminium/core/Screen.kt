package dev.than0s.aluminium.core

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(
    val name: String = "",
) {

    @Serializable
    data object SplashScreen : Screen(
        name = "Splash",
    )

    @Serializable
    data object SignInScreen : Screen(
        name = "SignIn",
    )

    @Serializable
    data object SettingScreen : Screen("Setting")

    @Serializable
    data object RegistrationScreen : Screen("Registration")

    @Serializable
    data object RegistrationRequestsScreen : Screen("Registration Requests")

    @Serializable
    data object PostUploadScreen : Screen("Post Upload")

    @Serializable
    data object SignOutScreen : Screen("Sign Out")

    @Serializable
    data object ForgotPasswordScreen : Screen("Forgot Password")

    @Serializable
    data object ChatListScreen : Screen("Chat List")

    @Serializable
    data object CreateProfileScreen : Screen("Create Profile")

    @Serializable
    data class PostsScreen(
        val userId: String? = null,
    ) : Screen("Posts")

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
}