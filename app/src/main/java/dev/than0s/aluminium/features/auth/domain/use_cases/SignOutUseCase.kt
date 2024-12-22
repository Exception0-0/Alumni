package dev.than0s.aluminium.features.auth.domain.use_cases

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): SimpleResource {
        return repository.signOut()
    }
}