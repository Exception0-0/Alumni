package dev.than0s.aluminium.features.register.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.core.data_class.RegistrationForm
import dev.than0s.aluminium.features.register.domain.repository.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestsListUseCase @Inject constructor(private val repository: RegistrationRepository) :
    UseCase<Unit, Flow<List<RegistrationForm>>> {
    override suspend fun invoke(param: Unit): Either<Failure, Flow<List<RegistrationForm>>> {
        // TODO: add business logic
        return repository.getRequestsList()
    }
}