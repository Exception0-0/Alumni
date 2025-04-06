package dev.than0s.aluminium.features.notification.domain.repository

import dev.than0s.aluminium.core.Resource
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.features.notification.domain.data_class.CloudNotification
import dev.than0s.aluminium.features.notification.domain.data_class.PushNotification

interface RepositoryMessaging {
    suspend fun addToken(token: String): SimpleResource
    suspend fun removeToken(token: String): SimpleResource
    suspend fun remoteNotification(notification: CloudNotification): SimpleResource
    suspend fun getNotifications(): Resource<List<CloudNotification>>
    suspend fun pushNotification(notification: PushNotification): SimpleResource
}