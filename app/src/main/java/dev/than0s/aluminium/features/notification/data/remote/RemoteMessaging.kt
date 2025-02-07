package dev.than0s.aluminium.features.notification.data.remote

import com.google.firebase.messaging.FirebaseMessaging
import dev.than0s.aluminium.core.data.remote.error.ServerException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RemoteMessaging {
    suspend fun subscribeChannel(channel: String)
    suspend fun unSubscribeChannel(channel: String)
}

class RemoteMessagingImple @Inject constructor(
    private val messaging: FirebaseMessaging
) : RemoteMessaging {
    override suspend fun subscribeChannel(channel: String) {
        try {
            messaging.subscribeToTopic(channel).await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun unSubscribeChannel(channel: String) {
        try {
            messaging.unsubscribeFromTopic(channel).await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }
}