package dev.than0s.aluminium.core.domain.use_case

import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.features.last_seen.domain.repository.RepositoryLastSeen
import javax.inject.Inject

class UseCaseUpdateUserStatus @Inject constructor(
    private val repository: RepositoryLastSeen
) {
    suspend operator fun invoke(): SimpleResource {
        return repository.updateLastSeenOnDisconnect()
    }
}