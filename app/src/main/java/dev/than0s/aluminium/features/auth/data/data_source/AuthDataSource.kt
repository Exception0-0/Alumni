package dev.than0s.aluminium.features.auth.data.data_source

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AuthDataSource {
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun forgetPassword(email: String)
    suspend fun signOut()
}

class AuthDataSourceImple @Inject constructor(private val auth: FirebaseAuth) :
    AuthDataSource {
    override suspend fun signIn(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun signUp(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun signOut() {
        try {
            auth.signOut()
        } catch (e: Exception) {
            throw ServerException(e.message.toString())
        }
    }

    override suspend fun forgetPassword(email: String) {
        try {
            auth.sendPasswordResetEmail(email).await()
        } catch (e: FirebaseAuthException) {
            throw ServerException(e.message.toString())
        }
    }
}