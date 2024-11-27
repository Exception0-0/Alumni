package dev.than0s.aluminium.features.auth.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.auth.domain.repository.AuthRepository
import dev.than0s.aluminium.features.auth.domain.data_class.EmailAuthParam
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: AuthRepository) :
    UseCase<EmailAuthParam, Unit> {
    override suspend fun invoke(param: EmailAuthParam): Either<Failure, Unit> {
        // TODO: add validation
        return repository.signUp(param.email, param.password)
    }
}