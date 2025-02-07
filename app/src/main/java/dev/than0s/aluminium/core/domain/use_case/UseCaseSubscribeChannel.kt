package dev.than0s.aluminium.core.domain.use_case

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.features.notification.domain.repository.RepositoryMessaging
import javax.inject.Inject

class UseCaseSubscribeChannel @Inject constructor(
    private val repository: RepositoryMessaging
) {
    suspend operator fun invoke(channel: String): SimpleResource {
        return repository.subscribeChannel(channel)
    }
}