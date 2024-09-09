package dev.than0s.aluminium.features.settings.data.repositories

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.settings.domain.repository.StorageRepository
import dev.than0s.mydiary.core.error.ServerException
import java.io.InputStream
import java.net.URL
import javax.inject.Inject

class StorageRepositoryImple @Inject constructor(
    private val repository: StorageRepository
) : StorageRepository {
    override suspend fun uploadProfileImage(image: InputStream): Either<ServerException, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun downloadProfileImage(): Either<ServerException, URL> {
        TODO("Not yet implemented")
    }
}