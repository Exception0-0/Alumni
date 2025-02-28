package dev.than0s.aluminium.features.notification.domain.repository

import dev.than0s.aluminium.core.SimpleResource

interface RepositoryMessaging {
    suspend fun subscribeChannel(channel: String): SimpleResource
    suspend fun unSubscribeChannel(channel: String): SimpleResource
    suspend fun setToken(token:String?): SimpleResource
}