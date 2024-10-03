package dev.than0s.aluminium.features.splash.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.splash.domain.repository.AccountRepository
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(private val repository: AccountRepository) :
    UseCase<Unit, String?> {
    override suspend fun invoke(param: Unit): Either<Failure, String?> {
        return repository.currentUserId
    }
}