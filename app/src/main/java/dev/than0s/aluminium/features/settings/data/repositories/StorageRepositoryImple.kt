package dev.than0s.aluminium.features.settings.data.repositories

import android.net.Uri
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.features.settings.data.data_source.StorageDataSource
import dev.than0s.aluminium.features.settings.domain.repository.StorageRepository
import dev.than0s.mydiary.core.error.ServerException
import javax.inject.Inject

class StorageRepositoryImple @Inject constructor(
    private val dataSource: StorageDataSource
) : StorageRepository {
    override suspend fun setProfileImage(image: Uri): Either<ServerException, Unit> {
        return try {
            dataSource.setProfileImage(image)
            Either.Right(Unit)
        } catch (e: ServerException) {
            Either.Left(e)
        }
    }

    override suspend fun getProfileImage(): Either<ServerException, Uri> {
        return try {
            Either.Right(dataSource.getProfileImage())
        } catch (e: ServerException) {
            Either.Left(e)
        }
    }
}