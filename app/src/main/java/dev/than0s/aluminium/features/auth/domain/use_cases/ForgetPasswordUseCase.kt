package dev.than0s.aluminium.features.auth.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class ForgetPasswordUseCase @Inject constructor(private val repository: AuthRepository) :
    UseCase<String, Unit> {
    override suspend fun invoke(email: String): Either<Failure, Unit> {
        // TODO: add validation
        return repository.forgetPassword(email)
    }
}