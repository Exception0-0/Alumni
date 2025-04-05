package dev.than0s.aluminium.features.notification.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dev.than0s.aluminium.core.data.remote.CLOUD_MESSAGING_TOKEN
import dev.than0s.aluminium.core.data.remote.CLOUD_NOTIFICATION
import dev.than0s.aluminium.core.data.remote.USERS
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.features.notification.domain.data_class.CloudNotification
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RemoteMessaging {
    suspend fun addToken(token: String)
    suspend fun removeToken(token: String)
    suspend fun removeNotification(notification: CloudNotification)
    suspend fun getNotifications(): List<CloudNotification>
}

class RemoteMessagingImple @Inject constructor(
    private val store: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : RemoteMessaging {
    override suspend fun addToken(token: String) {
        try {
            store.collection(USERS)
                .document(auth.currentUser!!.uid)
                .set(
                    mapOf(CLOUD_MESSAGING_TOKEN to FieldValue.arrayUnion(token)),
                    SetOptions.merge()
                )
                .await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun removeToken(token: String) {
        try {
            store.collection(USERS)
                .document(auth.currentUser!!.uid)
                .set(
                    mapOf(CLOUD_MESSAGING_TOKEN to FieldValue.arrayRemove(token)),
                    SetOptions.merge()
                )
                .await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun removeNotification(notification: CloudNotification) {
        try {
            store.collection(USERS)
                .document(auth.currentUser!!.uid)
                .collection(CLOUD_NOTIFICATION)
                .document(notification.id)
                .delete()
                .await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getNotifications(): List<CloudNotification> {
        return try {
            store.collection(USERS)
                .document(auth.currentUser!!.uid)
                .collection(CLOUD_NOTIFICATION)
                .get()
                .await()
                .toObjects(CloudNotification::class.java)
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }
}