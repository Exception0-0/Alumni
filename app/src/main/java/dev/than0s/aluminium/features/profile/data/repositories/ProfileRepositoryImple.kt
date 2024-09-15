package dev.than0s.aluminium.features.profile.data.repositories

import android.net.Uri
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.aluminium.features.profile.data.data_source.ProfileDataSource
import dev.than0s.aluminium.features.profile.domain.repository.ProfileRepository
import dev.than0s.mydiary.core.error.ServerException
import javax.inject.Inject

class ProfileRepositoryImple @Inject constructor(private val dataSource: ProfileDataSource) :
    ProfileRepository {

    override suspend fun getProfile(): Either<Failure, User?> {
        return try {
            Either.Right(dataSource.getUserProfile())
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }

    override suspend fun setProfile(profile: User): Either<Failure, Unit> {
        return try {
            dataSource.setUserProfile(profile)
            Either.Right(Unit)
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }

    override suspend fun setProfileImage(image: Uri): Either<Failure, Unit> {
        return try {
            dataSource.setProfileImage(image)
            Either.Right(Unit)
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }

    override suspend fun getProfileImage(): Either<Failure, Uri> {
        return try {
            Either.Right(dataSource.getProfileImage())
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }
}