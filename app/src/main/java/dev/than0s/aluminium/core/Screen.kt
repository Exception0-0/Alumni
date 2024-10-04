package dev.than0s.aluminium.core

import kotlinx.serialization.Serializable

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
    data class PostsScreen(
        val userId: String? = null
    ) : Screen()

    @Serializable
    data class ProfileScreen(
        val userId: String
    ) : Screen()

    @Serializable
    data class CommentsScreen(
        val postId: String
    ) : Screen()
}