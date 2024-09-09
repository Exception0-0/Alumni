package dev.than0s.aluminium.features.settings.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.mydiary.core.error.ServerException
import java.io.InputStream
import java.net.URL

interface StorageRepository {
    suspend fun uploadProfileImage(image: InputStream): Either<ServerException, Unit>
    suspend fun downloadProfileImage(): Either<ServerException, URL>
}