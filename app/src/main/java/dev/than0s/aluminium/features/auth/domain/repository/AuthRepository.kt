package dev.than0s.aluminium.features.auth.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.SimpleResource
import dev.than0s.aluminium.core.error.Failure

interface AuthRepository {
    suspend fun signIn(email: String, password: String): SimpleResource
    suspend fun signUp(email: String, password: String): SimpleResource
    suspend fun forgetPassword(email: String): SimpleResource
    suspend fun signOut(): SimpleResource
}