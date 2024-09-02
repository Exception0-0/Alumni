package dev.than0s.aluminium.features.auth.data.data_source

import com.google.firebase.auth.FirebaseAuth
import dev.than0s.aluminium.core.data_class.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AccountDataSource {
    val currentUserId: String
    val currentUser: Flow<User>
    suspend fun signOut()
    suspend fun deleteAccount()
}

class FirebaseAccountDataSourceImple @Inject constructor(private val auth: FirebaseAuth) :
    AccountDataSource {
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}