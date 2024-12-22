package dev.than0s.aluminium.features.auth.domain.data_class

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.presentation.TextFieldError

data class SignInResult(
    val emailError: TextFieldError? = null,
    val passwordError: TextFieldError? = null,
    val result: SimpleResource? = null
)