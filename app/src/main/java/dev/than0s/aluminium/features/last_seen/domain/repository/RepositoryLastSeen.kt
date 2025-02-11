package dev.than0s.aluminium.features.last_seen.domain.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.features.last_seen.domain.data_class.UserStatus
import kotlinx.coroutines.flow.Flow

interface RepositoryLastSeen {
    suspend fun updateLastSeenOnDisconnect(): SimpleResource
    fun getUserStatus(userId: String): Resource<Flow<UserStatus>>
}