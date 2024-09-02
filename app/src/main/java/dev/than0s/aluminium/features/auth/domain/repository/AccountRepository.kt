package dev.than0s.aluminium.features.auth.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.data_class.User
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    val currentUserId: Either<ServerException, String>
    val currentUser: Either<ServerException, Flow<User>>
    suspend fun signOut(): Either<ServerException, Unit>
    suspend fun deleteAccount(): Either<ServerException, Unit>
}