package dev.than0s.aluminium.features.settings.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.features.settings.domain.repository.StorageRepository
import java.net.URL
import javax.inject.Inject

class DownloadProfileImageUseCase @Inject constructor(private val repository: StorageRepository) :
    UseCase<Unit, URL> {
    override suspend fun invoke(param: Unit): Either<Failure, URL> {
        return when (val result = repository.downloadProfileImage()) {
            is Either.Left -> Either.Left(Failure(result.value.message))
            is Either.Right -> Either.Right(result.value)
        }
    }
}