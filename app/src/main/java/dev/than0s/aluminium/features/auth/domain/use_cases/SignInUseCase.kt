package dev.than0s.aluminium.features.auth.domain.use_cases

import dev.than0s.aluminium.core.domain.util.isValidEmail
import dev.than0s.aluminium.core.domain.util.isValidPassword
import dev.than0s.aluminium.features.auth.domain.repository.AuthRepository
import dev.than0s.aluminium.features.auth.domain.data_class.SignInResult
import dev.than0s.aluminium.core.presentation.TextFieldError
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): SignInResult {
        val emailError = email.let {
            if (it.isBlank()) TextFieldError.FieldEmpty
            else if (!isValidEmail(it)) TextFieldError.InvalidEmail
            else null
        }

        val passwordError = password.let {
            if (it.isBlank()) TextFieldError.FieldEmpty
            else if (!isValidPassword(it)) TextFieldError.InvalidPassword
            else null
        }

        if (emailError != null || passwordError != null) {
            return SignInResult(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        return SignInResult(
            result = repository.signIn(
                email = email,
                password = password
            )
        )
    }
}