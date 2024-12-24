package dev.than0s.aluminium.features.auth.presentation.screens.sign_out

sealed class SignOutEvents {
    data class SignOut(
        val restartApp: () -> Unit = {},
    ) : SignOutEvents()
}