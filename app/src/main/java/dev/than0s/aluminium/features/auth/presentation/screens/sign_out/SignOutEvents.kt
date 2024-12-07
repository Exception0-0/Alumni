package dev.than0s.aluminium.features.auth.presentation.screens.sign_out

sealed class SignOutEvents {
    data class signOut(
        val onSuccess: () -> Unit = {},
        val onComplete: () -> Unit = {},
    ) : SignOutEvents()
}