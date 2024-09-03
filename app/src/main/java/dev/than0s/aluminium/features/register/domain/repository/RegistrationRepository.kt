package dev.than0s.aluminium.features.register.domain.repository

import dev.than0s.aluminium.core.Either
import dev.than0s.aluminium.core.data_class.Failure
import dev.than0s.aluminium.features.register.data.data_source.RegistrationForm

interface RegistrationRepository {
    suspend fun register(form: RegistrationForm): Either<Failure, Unit>
}