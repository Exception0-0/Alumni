package dev.than0s.aluminium.features.settings.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.core.data_class.User
import dev.than0s.aluminium.features.settings.domain.repository.ProfileRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: ProfileRepository) :
    UseCase<Unit, User?> {
    override suspend fun invoke(param: Unit): Either<Failure, User?> {
        return when (val result = repository.getUserProfile()) {
            is Either.Left -> Either.Left(Failure(result.value.message))
            is Either.Right -> Either.Right(result.value)
        }
    }
}