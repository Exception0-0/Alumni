package dev.than0s.aluminium.features.splash.domain.use_cases

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.features.splash.domain.repository.AccountRepository
import javax.inject.Inject

class HasUserProfileCreatedUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend operator fun invoke(userId: String): Resource<Boolean> {
        return repository.hasUserProfileCreated(userId)
    }
}