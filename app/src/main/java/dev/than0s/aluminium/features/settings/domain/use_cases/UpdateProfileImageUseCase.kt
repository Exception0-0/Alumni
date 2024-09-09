package dev.than0s.aluminium.features.settings.domain.use_cases


import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.features.settings.domain.repository.StorageRepository
import java.net.URL
import javax.inject.Inject

class UpdateProfileImageUseCase @Inject constructor(private val repository: StorageRepository) :
    UseCase<URL, Unit> {
    override suspend fun invoke(param: URL): Either<Failure, Unit> {
        val stream = param.openStream()
        return when (val result = repository.uploadProfileImage(stream)) {
            is Either.Left -> Either.Left(Failure(result.value.message))
            is Either.Right -> Either.Right(Unit)
        }
    }
}