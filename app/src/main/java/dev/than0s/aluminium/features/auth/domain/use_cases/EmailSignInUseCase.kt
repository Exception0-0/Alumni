package dev.than0s.aluminium.features.auth.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.features.auth.domain.repository.EmailAuthRepository
import dev.than0s.mydiary.core.data_class.EmailAuthParam
import javax.inject.Inject

class EmailSignInUseCase @Inject constructor(private val repository: EmailAuthRepository) :
    UseCase<EmailAuthParam, Unit> {
    override suspend fun invoke(param: EmailAuthParam): Either<Failure, Unit> {
        // TODO: add validation
        return when (val result = repository.signIn(param.email, param.password)) {
            is Either.Left -> Either.Left(Failure(result.value.message))
            is Either.Right -> Either.Right(Unit)
        }
    }
}