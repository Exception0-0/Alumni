package dev.than0s.aluminium.features.last_seen.domain.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource

interface RepositoryLastSeen {
    suspend fun updateLastSeen(): SimpleResource
    suspend fun getLastSeen(userId: String): Resource<Long?>
}