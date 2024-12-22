package dev.than0s.aluminium.features.auth.domain.use_cases

import dev.than0s.aluminium.core.domain.util.isValidEmail
import dev.than0s.aluminium.features.auth.domain.data_class.ForgetPasswordResult
import dev.than0s.aluminium.features.auth.domain.repository.AuthRepository
import dev.than0s.aluminium.core.presentation.TextFieldError
import javax.inject.Inject

class ForgetPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): ForgetPasswordResult {
        val emailError = email.let{
            if (it.isBlank()) TextFieldError.FieldEmpty
            else if (!isValidEmail(it)) TextFieldError.InvalidEmail
            else null
        }

        emailError?.let {
            return ForgetPasswordResult(
                emailError = emailError
            )
        }

        return ForgetPasswordResult(
            result = repository.forgetPassword(email)
        )
    }
}