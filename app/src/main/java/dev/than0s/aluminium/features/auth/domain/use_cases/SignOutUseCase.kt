package dev.than0s.aluminium.features.auth.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val repository: AuthRepository) :
    UseCase<Unit, Unit> {
    override suspend fun invoke(param: Unit): Either<Failure, Unit> {
        // TODO: add validation
        return repository.signOut()
    }
}