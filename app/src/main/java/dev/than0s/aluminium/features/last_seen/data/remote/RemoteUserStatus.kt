package dev.than0s.aluminium.features.last_seen.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.snapshots
import dev.than0s.aluminium.core.data.remote.IS_ONLINE
import dev.than0s.aluminium.core.data.remote.LAST_SEEN
import dev.than0s.aluminium.core.data.remote.USER_STATUS
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.features.last_seen.domain.data_class.UserStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RemoteUserStatus {
    suspend fun updateLastSeenOnDisconnect()
    fun getUserStatus(userId: String): Flow<UserStatus>
}

class RemoteUserStatusImple @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : RemoteUserStatus {

    override suspend fun updateLastSeenOnDisconnect() {
        try {
            database.reference
                .child(USER_STATUS)
                .child(auth.currentUser!!.uid)
                .setValue(
                    mapOf(IS_ONLINE to true)
                )
                .await()
            println("here")
            database.reference
                .child(USER_STATUS)
                .child(auth.currentUser!!.uid)
                .onDisconnect()
                .setValue(
                    mapOf(
                        LAST_SEEN to ServerValue.TIMESTAMP,
                        IS_ONLINE to false
                    )
                )
                .await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override fun getUserStatus(userId: String): Flow<UserStatus> {
        return try {
            database.reference
                .child(USER_STATUS)
                .child(userId)
                .snapshots.map { snapshot ->
                    (snapshot.value as Map<*, *>).let {
                        UserStatus(
                            isOnline = it[IS_ONLINE] as Boolean,
                            lastSeen = it[LAST_SEEN] as Long? ?: 0L
                        )
                    }
                }

        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }
}