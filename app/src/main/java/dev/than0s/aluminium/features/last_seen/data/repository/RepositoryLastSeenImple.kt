package dev.than0s.aluminium.features.last_seen.data.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.last_seen.data.remote.RemoteLastSeen
import dev.than0s.aluminium.features.last_seen.domain.repository.RepositoryLastSeen
import javax.inject.Inject

class RepositoryLastSeenImple @Inject constructor(
    private val remote: RemoteLastSeen
) : RepositoryLastSeen {
    override suspend fun updateLastSeen(): SimpleResource {
        return try {
            Resource.Success(remote.updateLastSeen())
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun getLastSeen(userId: String): Resource<Long?> {
        return try {
            Resource.Success(remote.getLastSeen(userId))
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }
}