package dev.than0s.aluminium.features.auth.presentation.screens.sign_in

import dev.than0s.aluminium.core.domain.error.Error

data class SignInState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val emailError: Error? = null,
    val passwordError: Error? = null
)