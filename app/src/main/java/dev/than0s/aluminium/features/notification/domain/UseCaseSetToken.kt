package dev.than0s.aluminium.features.notification.domain

import dev.than0s.aluminium.features.notification.domain.repository.RepositoryMessaging
import javax.inject.Inject

class UseCaseSetToken @Inject constructor(
    private val repository: RepositoryMessaging
) {
    suspend operator fun invoke(token: String?) = repository.setToken(token)
}