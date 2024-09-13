package dev.than0s.aluminium.features.settings.domain.use_cases

import android.net.Uri
import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.features.settings.domain.repository.StorageRepository
import javax.inject.Inject

class SetPostFileUseCase @Inject constructor(private val repository: StorageRepository) :
    UseCase<Uri, String> {
    override suspend fun invoke(param: Uri): Either<Failure, String> {
        val fileId = System.currentTimeMillis().toString()
        return when (val result = repository.setFile(param, getPostsFilePath(fileId))) {
            is Either.Left -> Either.Left(Failure(result.value.message))
            is Either.Right -> Either.Right(fileId)
        }
    }
}