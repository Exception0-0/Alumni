package dev.than0s.aluminium.core.domain.use_case

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): SimpleResource {
        return repository.signOut()
    }
}