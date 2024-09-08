package dev.than0s.aluminium.features.settings.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.data_class.User
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    val userProfile: Either<ServerException, Flow<User?>>
    suspend fun updateProfile(): Either<ServerException, Unit>
}