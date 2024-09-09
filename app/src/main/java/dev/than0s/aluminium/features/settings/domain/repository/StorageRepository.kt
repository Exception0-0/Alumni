package dev.than0s.aluminium.features.settings.domain.repository

import android.net.Uri
import dev.than0s.aluminium.core.Either
import dev.than0s.mydiary.core.error.ServerException
import java.io.InputStream
import java.net.URL

interface StorageRepository {
    suspend fun setProfileImage(image: Uri): Either<ServerException, Unit>
    suspend fun getProfileImage(): Either<ServerException, Uri>
}