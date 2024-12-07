package dev.than0s.aluminium.features.auth.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.auth.domain.data_class.Email
import dev.than0s.aluminium.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class ForgetPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(param: Email): SimpleResource {
        return repository.forgetPassword(param.email)
    }
}