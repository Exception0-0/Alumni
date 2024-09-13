package dev.than0s.aluminium.features.settings.domain.repository

import android.net.Uri
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.data_class.Failure

interface StorageRepository {
    suspend fun setFile(image: Uri, path: String): Either<Failure, Unit>
    suspend fun getFile(path: String): Either<Failure, Uri>
}