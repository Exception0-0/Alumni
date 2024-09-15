package dev.than0s.aluminium.features.auth.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Either<Failure, Unit>
    suspend fun signUp(email: String, password: String): Either<Failure, Unit>
    suspend fun forgetPassword(email: String): Either<Failure, Unit>
    suspend fun signOut(): Either<Failure, Unit>
}