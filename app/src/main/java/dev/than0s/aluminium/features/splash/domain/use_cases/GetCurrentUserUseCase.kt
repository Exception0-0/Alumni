package dev.than0s.aluminium.features.splash.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.splash.domain.data_class.CurrentUser
import dev.than0s.aluminium.features.splash.domain.repository.AccountRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val repository: AccountRepository) :
    UseCase<Unit, CurrentUser> {
    override suspend fun invoke(param: Unit): Either<Failure, CurrentUser> {
        return repository.getCurrentUser()
    }
}