package dev.than0s.aluminium.features.profile.domain.repository

import android.net.Uri
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.profile.domain.data_class.ContactInfo
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.mydiary.core.error.ServerException

interface ProfileRepository {
    suspend fun getUserProfile(): Either<Failure, User?>
    suspend fun setUserProfile(profile: User): Either<Failure, Unit>
    suspend fun setContactInfo(contactInfo: ContactInfo): Either<Failure, Unit>
    suspend fun getContactInfo(): Either<Failure, ContactInfo>
}