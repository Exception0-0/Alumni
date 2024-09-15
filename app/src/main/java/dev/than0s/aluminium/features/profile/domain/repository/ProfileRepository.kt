package dev.than0s.aluminium.features.profile.domain.repository

import android.net.Uri
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.profile.domain.data_class.User
import dev.than0s.mydiary.core.error.ServerException

interface ProfileRepository {
    suspend fun getProfile(): Either<Failure, User?>
    suspend fun setProfile(profile: User): Either<Failure, Unit>
    suspend fun setProfileImage(image: Uri): Either<Failure, Unit>
    suspend fun getProfileImage(): Either<Failure, Uri>
}