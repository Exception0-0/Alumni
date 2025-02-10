package dev.than0s.aluminium.features.last_seen.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import dev.than0s.aluminium.core.data.remote.LAST_SEEN
import dev.than0s.aluminium.core.data.remote.error.ServerException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RemoteLastSeen {
    suspend fun updateLastSeen()
    suspend fun getLastSeen(userId: String): Long?
}

class RemoteLastSeenImple @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : RemoteLastSeen {
    override suspend fun updateLastSeen() {
        try {
            database.reference
                .child(LAST_SEEN)
                .child(auth.currentUser!!.uid)
                .setValue(ServerValue.TIMESTAMP)
                .await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun getLastSeen(userId: String): Long? {
        return try {
            database.reference
                .child(LAST_SEEN)
                .child(userId)
                .get()
                .await()
                .getValue(Long::class.java)
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }
}