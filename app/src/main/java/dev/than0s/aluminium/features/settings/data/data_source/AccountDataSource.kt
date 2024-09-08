package dev.than0s.aluminium.features.settings.data.data_source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import dev.than0s.aluminium.core.data_class.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AccountDataSource {
    val hasUser: Boolean
    suspend fun signOut()
    suspend fun deleteAccount()
}

class FirebaseAccountDataSourceImple @Inject constructor(private val auth: FirebaseAuth) :
    AccountDataSource {

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}