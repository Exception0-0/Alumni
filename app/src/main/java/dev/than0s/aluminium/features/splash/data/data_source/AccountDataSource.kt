package dev.than0s.aluminium.features.splash.data.data_source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AccountDataSource {
    val currentUserId: String?
}

class AccountDataSourceImple @Inject constructor(private val auth: FirebaseAuth) :
    AccountDataSource {

    override val currentUserId: String?
        get() = try {
            auth.currentUser?.uid
        } catch (e: FirebaseAuthException) {
            throw ServerException(e.message.toString())
        }
}