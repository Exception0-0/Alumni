package dev.than0s.aluminium.features.splash.data.data_source

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import dev.than0s.aluminium.core.data.remote.COLLEGE_INFO
import dev.than0s.aluminium.core.data.remote.PROFILE
import dev.than0s.aluminium.core.data.remote.error.ServerException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AccountDataSource {
    val currentUserId: String?
    suspend fun getUserRole(userId: String): String
    suspend fun hasUserProfileCreated(userId: String): Boolean
}

class AccountDataSourceImple @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore
) :
    AccountDataSource {

    override val currentUserId: String?
        get() = try {
            auth.currentUser?.uid
        } catch (e: FirebaseAuthException) {
            throw ServerException(e.message.toString())
        }

    override suspend fun getUserRole(userId: String): String {
        return try {
            store.collection(COLLEGE_INFO)
                .document(userId)
                .get()
                .await()
                .get("role")
                .toString()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun hasUserProfileCreated(userId: String): Boolean {
        return try {
            store.collection(PROFILE)
                .document(userId)
                .get()
                .await()
                .exists()
        } catch (e: FirebaseFirestoreException) {
            throw ServerException(e.message.toString())
        }
    }
}