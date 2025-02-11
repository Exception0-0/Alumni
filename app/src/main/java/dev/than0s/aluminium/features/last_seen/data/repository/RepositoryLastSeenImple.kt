package dev.than0s.aluminium.features.last_seen.data.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.last_seen.data.remote.RemoteUserStatus
import dev.than0s.aluminium.features.last_seen.domain.data_class.UserStatus
import dev.than0s.aluminium.features.last_seen.domain.repository.RepositoryLastSeen
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryLastSeenImple @Inject constructor(
    private val remote: RemoteUserStatus
) : RepositoryLastSeen {
    override suspend fun updateLastSeenOnDisconnect(): SimpleResource {
        return try {
            Resource.Success(remote.updateLastSeenOnDisconnect())
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override fun getUserStatus(userId: String): Resource<Flow<UserStatus>> {
        return try {
            Resource.Success(remote.getUserStatus(userId))
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }
}