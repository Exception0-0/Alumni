package dev.than0s.aluminium.features.auth.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.features.auth.domain.repository.AccountRepository
import javax.inject.Inject

class AccountHasUserUseCase @Inject constructor(private val repository: AccountRepository) :
    UseCase<Unit, Boolean> {
    override suspend fun invoke(param: Unit): Either<Failure, Boolean> {
        return when (val hasUser = repository.hasUser) {
            is Either.Left -> Either.Left(Failure(message = hasUser.value.message))
            is Either.Right -> Either.Right(hasUser.value)
        }
    }
}