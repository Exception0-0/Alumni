package dev.than0s.aluminium.features.auth.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.mydiary.core.error.ServerException

interface EmailAuthRepository {
    suspend fun signIn(email: String, password: String): Either<ServerException, Unit>
    suspend fun signUp(email: String, password: String): Either<ServerException, Unit>
}