package dev.than0s.aluminium.features.auth.presentation.screens.forget_password

import dev.than0s.aluminium.core.domain.error.Error


data class ForgetPasswordState(
    val isLoading: Boolean = false,
    val email: String = "",
    val emailError: Error? = null
)
