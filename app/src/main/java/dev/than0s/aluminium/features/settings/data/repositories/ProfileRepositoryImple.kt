package dev.than0s.aluminium.features.settings.data.repositories

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.data_class.User
import dev.than0s.aluminium.features.settings.data.data_source.ProfileDataSource
import dev.than0s.aluminium.features.settings.domain.repository.ProfileRepository
import dev.than0s.mydiary.core.error.ServerException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImple @Inject constructor(private val source: ProfileDataSource) :
    ProfileRepository {

    override suspend fun getUserProfile(): Either<ServerException, User?> {
        return try {
            Either.Right(source.getUserProfile())
        } catch (e: ServerException) {
            Either.Left(e)
        }
    }

    override suspend fun setUserProfile(profile: User): Either<ServerException, Unit> {
        return try {
            source.setUserProfile(profile)
            Either.Right(Unit)
        } catch (e: ServerException) {
            Either.Left(e)
        }
    }
}