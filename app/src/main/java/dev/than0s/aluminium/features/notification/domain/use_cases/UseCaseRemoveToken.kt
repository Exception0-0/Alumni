package dev.than0s.aluminium.features.notification.domain.use_cases

import dev.than0s.aluminium.features.notification.domain.repository.RepositoryMessaging
import javax.inject.Inject

class UseCaseRemoveToken @Inject constructor(
    private val repository: RepositoryMessaging
) {
    suspend operator fun invoke(token: String) = repository.removeToken(token)
}