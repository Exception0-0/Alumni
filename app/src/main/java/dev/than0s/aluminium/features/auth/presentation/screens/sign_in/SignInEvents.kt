package dev.than0s.aluminium.features.auth.presentation.screens.sign_in

sealed class SignInEvents {
    data class OnEmailChanged(
        val email: String = ""
    ) : SignInEvents()

    data class OnPasswordChange(
        val password: String = ""
    ) : SignInEvents()

    data class OnSignInClick(
        val restartApp: () -> Unit = {},
    ) : SignInEvents()

    data object OnPasswordVisibilityChange : SignInEvents()
}