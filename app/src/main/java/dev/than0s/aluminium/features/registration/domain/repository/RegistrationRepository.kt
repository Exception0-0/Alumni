package dev.than0s.aluminium.features.registration.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.error.Failure
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm

interface RegistrationRepository {
    suspend fun submitRegistration(form: RegistrationForm): Either<Failure, Unit>
}