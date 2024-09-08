package dev.than0s.aluminium.features.settings.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.core.data_class.User
import dev.than0s.aluminium.features.settings.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountCurrentUserUseCase @Inject constructor(private val repository: AccountRepository) :
    UseCase<Unit, Flow<User>> {
    override suspend fun invoke(param: Unit): Either<Failure, Flow<User>> {
        return when (val result = repository.currentUser) {
            is Either.Left -> Either.Left(Failure(result.value.message))
            is Either.Right -> Either.Right(result.value)
        }
    }
}