package dev.than0s.aluminium.features.notification.data.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.core.presentation.utils.UiText
import dev.than0s.aluminium.features.notification.data.remote.RemoteMessaging
import dev.than0s.aluminium.features.notification.domain.data_class.CloudNotification
import dev.than0s.aluminium.features.notification.domain.data_class.PushNotification
import dev.than0s.aluminium.features.notification.domain.repository.RepositoryMessaging
import javax.inject.Inject

class RepositoryMessagingImple @Inject constructor(
    private val remoteMessaging: RemoteMessaging
) : RepositoryMessaging {
    override suspend fun addToken(token: String): SimpleResource {
        return try {
            remoteMessaging.addToken(token)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun removeToken(token: String): SimpleResource {
        return try {
            remoteMessaging.removeToken(token)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun removeNotification(notification: CloudNotification): SimpleResource {
        return try {
            remoteMessaging.removeNotification(notification)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun getNotifications(): Resource<List<CloudNotification>> {
        return try {
            Resource.Success(remoteMessaging.getNotifications())
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }

    override suspend fun pushNotification(notification: PushNotification): SimpleResource {
        return try {
            remoteMessaging.pushNotification(notification)
            Resource.Success(Unit)
        } catch (e: ServerException) {
            Resource.Error(UiText.DynamicString(e.message))
        }
    }
}