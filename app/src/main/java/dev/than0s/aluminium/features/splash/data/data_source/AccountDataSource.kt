package dev.than0s.aluminium.features.splash.data.data_source

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AccountDataSource {
    val currentUserId: String?
    suspend fun getUserRole(userId: String): String
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
            store.collection(USER_COLLEGE_INFO)
                .document(userId)
                .get()
                .await()
                .get("category")
                .toString()
        } catch (e: FirebaseException) {
            throw ServerException(e.message.toString())
        }
    }

    companion object {
        const val USER_COLLEGE_INFO = "college_info"
    }
}