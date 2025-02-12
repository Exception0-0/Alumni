package dev.than0s.aluminium.features.last_seen.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.snapshots
import dev.than0s.aluminium.core.data.remote.USER_STATUS
import dev.than0s.aluminium.core.data.remote.error.ServerException
import dev.than0s.aluminium.features.last_seen.domain.data_class.UserStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RemoteUserStatus {
    suspend fun updateUserStatusToOnline()
    fun getUserStatus(userId: String): Flow<UserStatus>
}

class RemoteUserStatusImple @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : RemoteUserStatus {

    override suspend fun updateUserStatusToOnline() {
        try {
            database.reference
                .child(USER_STATUS)
                .child(auth.currentUser!!.uid)
                .setValue(-1) // -1 for online
                .await()

            onDisconnect()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    private suspend fun onDisconnect() {
        try {
            database.reference
                .child(USER_STATUS)
                .child(auth.currentUser!!.uid)
                .onDisconnect()
                .setValue(ServerValue.TIMESTAMP)
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
                    (snapshot.getValue(Long::class.java) ?: 0L).let {
                        UserStatus(
                            isOnline = it == -1L,
                            lastSeen = if (it == -1L) System.currentTimeMillis() else it
                        )
                    }
                }
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }
}