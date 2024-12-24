package dev.than0s.aluminium.features.auth.presentation.screens.forget_password

sealed class ForgetPasswordEvents {
    data class OnForgetPasswordClick(
        val popScreen: () -> Unit = {},
    ) : ForgetPasswordEvents()

    data class OnEmailChange(
        val email: String = ""
    ) : ForgetPasswordEvents()
}