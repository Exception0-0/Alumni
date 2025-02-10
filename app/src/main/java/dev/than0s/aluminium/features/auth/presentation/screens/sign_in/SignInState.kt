package dev.than0s.aluminium.features.auth.presentation.screens.sign_in

import dev.than0s.aluminium.core.domain.error.PreferredError

data class SignInState(
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val email: String = "",
    val password: String = "",
    val emailError: PreferredError? = null,
    val passwordError: PreferredError? = null
)