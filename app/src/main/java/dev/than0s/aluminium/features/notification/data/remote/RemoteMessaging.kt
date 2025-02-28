package dev.than0s.aluminium.features.notification.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dev.than0s.aluminium.core.data.remote.FCM
import dev.than0s.aluminium.core.data.remote.error.ServerException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RemoteMessaging {
    suspend fun subscribeChannel(channel: String)
    suspend fun unSubscribeChannel(channel: String)
    suspend fun setToken(token: String?)
}

class RemoteMessagingImple @Inject constructor(
    private val messaging: FirebaseMessaging,
    private val store: FirebaseFirestore,
    private val auth: FirebaseAuth,
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

    override suspend fun setToken(token: String?) {
        try {
            store.collection(FCM)
                .add(
                    Token(
                        userId = auth.currentUser!!.uid,
                        token = token
                    )
                )
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }
}

private data class Token(
    @DocumentId
    val userId: String = "",
    val token: String? = null,
)