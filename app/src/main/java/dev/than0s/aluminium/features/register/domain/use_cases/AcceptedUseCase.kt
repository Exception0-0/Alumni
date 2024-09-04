package dev.than0s.aluminium.features.register.domain.use_cases

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.UseCase
import dev.than0s.aluminium.core.data_class.AdminRequest
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.features.register.domain.repository.RegistrationRepository
import javax.inject.Inject

class AcceptedUseCase @Inject constructor(private val repository: RegistrationRepository) :
    UseCase<AdminRequest, Unit> {
    override suspend fun invoke(param: AdminRequest): Either<Failure, Unit> {
        // todo add business logic
        return repository.accepted(param)
    }
}