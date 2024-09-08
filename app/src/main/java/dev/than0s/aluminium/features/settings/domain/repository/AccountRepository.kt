package dev.than0s.aluminium.features.settings.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.data_class.User
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    val currentUser: Either<ServerException, Flow<User>>
    val hasUser: Either<ServerException, Boolean>
    suspend fun signOut(): Either<ServerException, Unit>
    suspend fun deleteAccount(): Either<ServerException, Unit>
}