package dev.than0s.aluminium.features.settings.data.repositories

import android.net.Uri
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.features.settings.data.data_source.StorageDataSource
import dev.than0s.aluminium.features.settings.domain.repository.StorageRepository
import dev.than0s.mydiary.core.error.ServerException
import javax.inject.Inject

class StorageRepositoryImple @Inject constructor(
    private val dataSource: StorageDataSource
) : StorageRepository {
    override suspend fun setFile(image: Uri, path: String): Either<Failure, Unit> {
        return try {
            dataSource.setFile(image, path)
            Either.Right(Unit)
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }

    override suspend fun getFile(path: String): Either<Failure, Uri> {
        return try {
            Either.Right(dataSource.getFile(path))
        } catch (e: ServerException) {
            Either.Left(Failure(e.message))
        }
    }
}