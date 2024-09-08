package dev.than0s.aluminium.features.settings.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.features.settings.domain.repository.AccountRepository
import javax.inject.Inject

class AccountSignOutUseCase @Inject constructor(private val repository: AccountRepository) :
    UseCase<Unit, Unit> {
    override suspend fun invoke(param: Unit): Either<Failure, Unit> {
        return when (val result = repository.signOut()) {
            is Either.Left -> Either.Left(Failure(result.value.message))
            is Either.Right -> Either.Right(Unit)
        }
    }
}