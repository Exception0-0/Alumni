package dev.than0s.aluminium.features.register.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.core.data_class.RegistrationForm
import dev.than0s.aluminium.features.register.domain.repository.RegistrationRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val repository: RegistrationRepository) :
    UseCase<RegistrationForm, Unit> {
    override suspend fun invoke(param: RegistrationForm): Either<Failure, Unit> {
        // TODO: add business logic
        return repository.register(param)
    }
}