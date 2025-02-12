package dev.than0s.aluminium.features.splash.domain.use_cases

import dev.than0s.aluminium.features.splash.domain.repository.AccountRepository
import javax.inject.Inject

class UseCaseGetCurrentUserId @Inject constructor(
    private val repository: AccountRepository
) {
    val currentUserId get() = repository.currentUserId
}