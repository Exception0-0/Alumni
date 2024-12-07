package dev.than0s.aluminium.features.auth.presentation.screens.forget_password

sealed class ForgetPasswordEvents {
    data class onForgetPasswordClick(
        val onSuccess: () -> Unit = {},
        val onComplete: () -> Unit = {},
    ) : ForgetPasswordEvents()

    data class onEmailChange(
        val email: String = ""
    ) : ForgetPasswordEvents()
}