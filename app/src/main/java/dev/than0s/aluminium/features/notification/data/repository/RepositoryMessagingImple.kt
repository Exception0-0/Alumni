package dev.than0s.aluminium.features.notification.data.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.notification.data.remote.RemoteMessaging
import dev.than0s.aluminium.features.notification.domain.repository.RepositoryMessaging
import javax.inject.Inject

class RepositoryMessagingImple @Inject constructor(
    private val remoteMessaging: RemoteMessaging
) : RepositoryMessaging {
    override suspend fun subscribeChannel(channel: String): SimpleResource {
        return try {
            remoteMessaging.subscribeChannel(channel)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun unSubscribeChannel(channel: String): SimpleResource {
        return try {
            remoteMessaging.unSubscribeChannel(channel)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }
}