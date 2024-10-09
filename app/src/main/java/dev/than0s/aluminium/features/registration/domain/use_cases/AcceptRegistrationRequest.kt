package dev.than0s.aluminium.features.registration.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.features.registration.domain.repository.RegistrationRepository
import javax.inject.Inject

class AcceptRegistrationRequest @Inject constructor(private val repository: RegistrationRepository) :
    UseCase<RegistrationForm, Unit> {
    override suspend fun invoke(param: RegistrationForm): Either<Failure, Unit> {
        val form = param.copy(
            status = param.status.copy(
                approvalStatus = true,
                accountGeneratedStatus = false,
            )
        )
        return repository.setRegistration(form)
    }
}