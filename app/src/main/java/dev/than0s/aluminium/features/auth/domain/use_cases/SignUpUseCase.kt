package dev.than0s.aluminium.features.auth.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.auth.domain.repository.AuthRepository
import dev.than0s.aluminium.features.auth.domain.data_class.EmailAuthParam
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(param: EmailAuthParam): SimpleResource {
        // TODO: add validation
        return repository.signUp(param.email, param.password)
    }
}