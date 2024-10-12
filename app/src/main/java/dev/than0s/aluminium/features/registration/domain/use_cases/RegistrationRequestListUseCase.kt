package dev.than0s.aluminium.features.registration.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.domain.repository.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistrationRequestListUseCase @Inject constructor(private val repository: RegistrationRepository) :
    UseCase<Unit, Flow<List<RegistrationForm>>> {
    override suspend fun invoke(param: Unit): Either<Failure, Flow<List<RegistrationForm>>> {
        return repository.registrationList()
    }
}