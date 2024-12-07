package dev.than0s.aluminium.features.auth.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.auth.domain.data_class.EmailAuthParam
import dev.than0s.aluminium.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): SimpleResource {
        // TODO: add validation
        return repository.signOut()
    }
}