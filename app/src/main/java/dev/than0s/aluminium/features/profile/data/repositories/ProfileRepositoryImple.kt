package dev.than0s.aluminium.features.profile.data.repositories

import android.net.Uri
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.aluminium.features.profile.data.data_source.ProfileDataSource
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.features.profile.domain.repository.ProfileRepository
import dev.than0s.mydiary.core.error.ServerException
import javax.inject.Inject

class ProfileRepositoryImple @Inject constructor(private val dataSource: ProfileDataSource) :
    ProfileRepository {

    override suspend fun getUserProfile(): Either<Failure, User?> {
        return try {
            Either.Right(dataSource.getUserProfile())
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }

    override suspend fun setUserProfile(profile: User): Either<Failure, Unit> {
        return try {
            dataSource.setUserProfile(profile)
            Either.Right(Unit)
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }

    override suspend fun setContactInfo(contactInfo: ContactInfo): Either<Failure, Unit> {
        return try {
            dataSource.setContactInfo(contactInfo)
            Either.Right(Unit)
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }

    override suspend fun getContactInfo(): Either<Failure, ContactInfo> {
        return try {
            Either.Right(dataSource.getContactInfo())
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }
}