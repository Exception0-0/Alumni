package dev.than0s.aluminium.features.splash.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.splash.domain.repository.AccountRepository
import javax.inject.Inject

class AccountHasUserUseCase @Inject constructor(private val repository: AccountRepository) :
    UseCase<Unit, Boolean> {
    override suspend fun invoke(param: Unit): Either<Failure, Boolean> {
        return repository.hasUser
    }
}