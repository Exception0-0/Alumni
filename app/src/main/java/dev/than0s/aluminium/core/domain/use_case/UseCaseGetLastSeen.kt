package dev.than0s.aluminium.core.domain.use_case

import dev.than0s.aluminium.features.last_seen.domain.repository.RepositoryLastSeen
import javax.inject.Inject

class UseCaseGetLastSeen @Inject constructor(
    private val repository: RepositoryLastSeen
) {
    suspend operator fun invoke(userId: String) = repository.getLastSeen(userId)
}