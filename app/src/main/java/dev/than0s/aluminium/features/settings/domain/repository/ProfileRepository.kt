package dev.than0s.aluminium.features.settings.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.data_class.User
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUserProfile(): Either<ServerException, User?>
    suspend fun setUserProfile(profile: User): Either<ServerException, Unit>
}