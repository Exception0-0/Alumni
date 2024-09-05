package dev.than0s.aluminium.features.register.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.core.data_class.RegistrationForm
import dev.than0s.aluminium.features.register.domain.repository.RegistrationRepository
import javax.inject.Inject

class AcceptedUseCase @Inject constructor(private val repository: RegistrationRepository) :
    UseCase<RegistrationForm, Unit> {
    override suspend fun invoke(param: RegistrationForm): Either<Failure, Unit> {
        // logic: update Registration form status
        val updatedParam = param.copy(
            status = param.status.copy(
                approvalStatus = true,
                accountGeneratedStatus = false,
            )
        )
        return repository.accept(updatedParam)
    }
}